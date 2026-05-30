package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class BombSkeleton implements Listener {
    private final ConfigManager config;
    private final NamespacedKey key;

    public BombSkeleton(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "bomb_skeleton");
    }

    @EventHandler
    public void onSkeletonSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (Math.random() >= config.getBombSkeletonSpawnChance()) return;

        skeleton.getEquipment().setItem(EquipmentSlot.HEAD, ItemStack.of(Material.TNT));

        var health = skeleton.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) health.setBaseValue(8);

        skeleton.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onBombSkeletonHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Skeleton skeleton)) return;
        var pd = skeleton.getPersistentDataContainer();
        if (!pd.getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        Location loc = arrow.getLocation();
        arrow.getWorld().createExplosion(loc, 1.5F, false, false, arrow);
        arrow.remove();
    }
}
