package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CorruptedGolemTask {

    public CorruptedGolemTask(JavaPlugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "corrupted_golem");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (var world : Bukkit.getWorlds()) {
                    for (var golem : world.getEntitiesByClass(IronGolem.class)) {
                        if (!golem.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) continue;

                        golem.setFireTicks(80); // постоянный огонь (голем иммунен к урону от него)

                        Player nearest = null;
                        double nearestDist = Double.MAX_VALUE;
                        for (Player p : world.getPlayers()) {
                            double d = golem.getLocation().distanceSquared(p.getLocation());
                            if (d < nearestDist) { nearest = p; nearestDist = d; }
                        }
                        if (nearest == null || nearestDist > 32 * 32) continue;

                        if (golem instanceof Mob mob) mob.setTarget(nearest);
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 40L);
    }
}
