package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.rtdxe.survivalEnvelope.util.BlockTracker;

public class SiegeZombieTask {

    private static final BlockFace[] HORIZONTAL = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};

    public SiegeZombieTask(JavaPlugin plugin, BlockTracker tracker) {
        NamespacedKey siegeKey = new NamespacedKey(plugin, "siege_zombie");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (var world : Bukkit.getWorlds()) {
                    if (world.getPlayers().isEmpty()) continue;

                    for (var zombie : world.getEntitiesByClass(Zombie.class)) {
                        if (!zombie.getPersistentDataContainer().getOrDefault(siegeKey, PersistentDataType.BOOLEAN, false)) continue;

                        boolean nearPlayer = world.getPlayers().stream()
                                .anyMatch(p -> zombie.getLocation().distanceSquared(p.getLocation()) < 400);
                        if (!nearPlayer) continue;

                        Block base = zombie.getLocation().getBlock();
                        outer:
                        for (int dy = 0; dy <= 1; dy++) {
                            Block row = base.getRelative(BlockFace.UP, dy);
                            for (BlockFace face : HORIZONTAL) {
                                Block candidate = row.getRelative(face);
                                if (!tracker.isPlayerPlaced(candidate)) continue;
                                if (!candidate.getType().isSolid()) continue;

                                // Снимаем с трекера сразу, чтобы другой осадный не взял тот же блок
                                tracker.remove(candidate.getLocation());
                                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                                    if (candidate.getType().isSolid()) {
                                        candidate.breakNaturally(); // выпадают дропы
                                    }
                                }, 40L); // 2 секунды "рубки"
                                break outer;
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 40L, 60L); // проверка каждые 3 секунды
    }
}
