package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MagneticCreeperTask {

    public MagneticCreeperTask(JavaPlugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "magnetic_creeper");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (var world : Bukkit.getWorlds()) {
                    for (var creeper : world.getEntitiesByClass(Creeper.class)) {
                        if (!creeper.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) continue;
                        if (!creeper.isIgnited()) continue;

                        Player nearest = null;
                        double nearestDist = Double.MAX_VALUE;
                        for (Player p : world.getPlayers()) {
                            double d = creeper.getLocation().distanceSquared(p.getLocation());
                            if (d < nearestDist) { nearest = p; nearestDist = d; }
                        }
                        if (nearest == null || nearestDist < 2 * 2) continue;

                        Vector pull = creeper.getLocation().toVector()
                                .subtract(nearest.getLocation().toVector())
                                .normalize()
                                .multiply(0.45);
                        nearest.setVelocity(nearest.getVelocity().add(pull));

                        world.spawnParticle(Particle.CRIT, creeper.getLocation().add(0, 1, 0), 4, 0.3, 0.4, 0.3, 0);
                    }
                }
            }
        }.runTaskTimer(plugin, 10L, 10L);
    }
}
