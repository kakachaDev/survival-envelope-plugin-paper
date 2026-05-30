package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class ShieldSkeleton implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public ShieldSkeleton(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "shield_skeleton");
    }

    @EventHandler
    public void onSkeletonSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (Math.random() >= config.getShieldSkeletonSpawnChance()) return;

        var eq = skeleton.getEquipment();
        eq.setItemInOffHand(new ItemStack(Material.SHIELD));
        eq.setItemInOffHandDropChance(0f);

        skeleton.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        skeleton.setCustomName("§7Щитоносец");
        skeleton.setCustomNameVisible(true);
    }

    @EventHandler
    public void onSkeletonHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (!skeleton.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        // Разворачиваем projectile до реального атакующего
        Entity damager = event.getDamager();
        Entity attacker = (damager instanceof Projectile proj && proj.getShooter() instanceof Entity e) ? e : damager;

        Vector toAttacker = attacker.getLocation().toVector()
                .subtract(skeleton.getLocation().toVector())
                .normalize();
        Vector facing = skeleton.getLocation().getDirection();

        // dot > 0.5 означает угол < 60°: скелет смотрит на атакующего
        if (facing.dot(toAttacker) > 0.5) {
            event.setDamage(event.getDamage() * 0.15); // блокирует 85% урона
        }
    }
}
