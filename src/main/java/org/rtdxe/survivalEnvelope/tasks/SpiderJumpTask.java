package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

import java.util.Random;

public class SpiderJumpTask {

    private final Random random = new Random();

    public SpiderJumpTask(ConfigManager config, JavaPlugin plugin) {

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!config.isSpiderJumpEnabled()) return;
                for (var world : Bukkit.getWorlds()) {
                    long time = world.getTime();
                    boolean isNight = time >= 13000 && time <= 23000;
                    if (config.getSpiderJumpOnlyNight() && !isNight) continue;

                    for (var entity : world.getEntitiesByClass(Spider.class)) {

                        Player nearest = null;
                        double nearestDist = 64 * 64; // distanceSquared, не линейная

                        for (Player p : world.getPlayers()) {
                            double dist = entity.getLocation().distanceSquared(p.getLocation());
                            if (dist < nearestDist) {
                                nearest = p;
                                nearestDist = dist;
                            }
                        }

                        if (nearest == null) continue;

                        if (random.nextDouble() > config.getSpiderJumpChance()) continue;

                        Location loc = entity.getLocation();
                        Vector direction = nearest.getLocation().toVector().subtract(loc.toVector()).normalize();
                        direction.multiply(config.getSpiderJumpStrength());
                        direction.setY(0.5);
                        entity.setVelocity(direction);
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 40L);
    }
}
