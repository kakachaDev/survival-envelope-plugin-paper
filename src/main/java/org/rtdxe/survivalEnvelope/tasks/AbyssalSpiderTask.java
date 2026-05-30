package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbyssalSpiderTask {

    public AbyssalSpiderTask(JavaPlugin plugin) {
        NamespacedKey spiderKey = new NamespacedKey(plugin, "abyssal_spider");
        NamespacedKey webKey    = new NamespacedKey(plugin, "abyssal_web");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (var world : Bukkit.getWorlds()) {
                    for (var spider : world.getEntitiesByClass(Spider.class)) {
                        if (!spider.getPersistentDataContainer().getOrDefault(spiderKey, PersistentDataType.BOOLEAN, false)) continue;

                        Player nearest = null;
                        double nearestDist = Double.MAX_VALUE;
                        for (Player p : world.getPlayers()) {
                            double d = spider.getLocation().distanceSquared(p.getLocation());
                            if (d < nearestDist) { nearest = p; nearestDist = d; }
                        }
                        if (nearest == null || nearestDist > 12 * 12) continue;

                        Vector dir = nearest.getEyeLocation().toVector()
                                .subtract(spider.getLocation().add(0, 0.5, 0).toVector())
                                .normalize();
                        Snowball web = spider.launchProjectile(Snowball.class);
                        web.setVelocity(dir.multiply(1.5));
                        web.getPersistentDataContainer().set(webKey, PersistentDataType.BOOLEAN, true);
                    }
                }
            }
        }.runTaskTimer(plugin, 60L, 60L);
    }
}
