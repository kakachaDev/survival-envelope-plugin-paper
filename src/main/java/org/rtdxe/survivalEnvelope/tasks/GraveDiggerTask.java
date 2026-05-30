package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class GraveDiggerTask {

    private static final Set<Material> DIGGABLE = Set.of(
            Material.DIRT, Material.GRASS_BLOCK, Material.SAND, Material.GRAVEL,
            Material.ROOTED_DIRT, Material.COARSE_DIRT, Material.DIRT_PATH,
            Material.CLAY, Material.PODZOL, Material.MYCELIUM,
            Material.FARMLAND, Material.SOUL_SAND, Material.SOUL_SOIL
    );

    public GraveDiggerTask(JavaPlugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "grave_digger");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (var world : Bukkit.getWorlds()) {
                    for (var zombie : world.getEntitiesByClass(Zombie.class)) {
                        if (!zombie.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) continue;

                        Player nearest = null;
                        double nearestDist = Double.MAX_VALUE;
                        for (Player p : world.getPlayers()) {
                            double d = zombie.getLocation().distanceSquared(p.getLocation());
                            if (d < nearestDist) { nearest = p; nearestDist = d; }
                        }
                        if (nearest == null || nearestDist > 20 * 20) continue;

                        double dx = nearest.getLocation().getX() - zombie.getLocation().getX();
                        double dz = nearest.getLocation().getZ() - zombie.getLocation().getZ();
                        int bx, bz;
                        if (Math.abs(dx) >= Math.abs(dz)) {
                            bx = zombie.getLocation().getBlockX() + (int) Math.signum(dx);
                            bz = zombie.getLocation().getBlockZ();
                        } else {
                            bx = zombie.getLocation().getBlockX();
                            bz = zombie.getLocation().getBlockZ() + (int) Math.signum(dz);
                        }
                        int by = zombie.getLocation().getBlockY();

                        for (int dy = 0; dy <= 1; dy++) {
                            Block block = world.getBlockAt(bx, by + dy, bz);
                            if (DIGGABLE.contains(block.getType())) block.breakNaturally();
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 40L, 60L);
    }
}
