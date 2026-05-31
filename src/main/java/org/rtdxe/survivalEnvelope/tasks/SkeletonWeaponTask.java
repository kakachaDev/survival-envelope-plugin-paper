package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class SkeletonWeaponTask {

    private final ItemStack bow   = new ItemStack(Material.BOW);
    private final ItemStack sword = new ItemStack(Material.IRON_SWORD);

    public SkeletonWeaponTask(ConfigManager config, JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!config.isSkeletonWeaponSwitchEnabled()) return;

                for (var world : Bukkit.getWorlds()) {
                    for (var skeleton : world.getEntitiesByClass(Skeleton.class)) {
                        EntityEquipment eq = skeleton.getEquipment();
                        ItemStack main = eq.getItemInMainHand();
                        ItemStack off  = eq.getItemInOffHand();

                        if (main.isEmpty() && off.isEmpty()) continue;

                        Player nearest = null;
                        double nearestDist = Double.MAX_VALUE;
                        for (Player p : world.getPlayers()) {
                            double dist = skeleton.getLocation().distanceSquared(p.getLocation());
                            if (dist < nearestDist) { nearest = p; nearestDist = dist; }
                        }
                        if (nearest == null) continue;

                        if (nearestDist <= 16 * 16) {
                            if (main.getType().toString().contains("BOW")) eq.setItemInMainHand(sword);
                        } else {
                            if (main.getType().toString().contains("SWORD")) eq.setItemInMainHand(bow);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 40L);
    }
}
