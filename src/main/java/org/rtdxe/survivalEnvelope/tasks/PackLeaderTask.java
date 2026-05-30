package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PackLeaderTask {

    public PackLeaderTask(JavaPlugin plugin) {
        NamespacedKey leaderKey = new NamespacedKey(plugin, "pack_leader");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (var world : Bukkit.getWorlds()) {
                    if (world.getPlayers().isEmpty()) continue;

                    for (var zombie : world.getEntitiesByClass(Zombie.class)) {
                        if (!zombie.getPersistentDataContainer().getOrDefault(leaderKey, PersistentDataType.BOOLEAN, false)) continue;

                        world.getNearbyEntities(zombie.getLocation(), 15, 15, 15).stream()
                                .filter(e -> e instanceof Zombie z && z != zombie)
                                .map(e -> (Zombie) e)
                                .forEach(z -> {
                                    // ambient=true убирает частицы (не спамит экран)
                                    z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,    60, 0, true, false));
                                    z.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 60, 0, true, false));
                                });
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 40L);
    }
}
