package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class BoneburstSkeleton implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public BoneburstSkeleton(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "boneburst_skeleton");
    }

    @EventHandler
    public void onSkeletonSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (Math.random() >= config.getBoneburstSkeletonSpawnChance()) return;

        skeleton.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

        skeleton.getEquipment().setItemInOffHand(new ItemStack(Material.BONE));
        skeleton.getEquipment().setItemInOffHandDropChance(0f);
    }

    @EventHandler
    public void onSkeletonDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (!skeleton.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        Location loc = skeleton.getEyeLocation();
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45.0);
            Vector dir = new Vector(Math.cos(angle), 0.1, Math.sin(angle)).normalize();
            Arrow arrow = loc.getWorld().spawn(loc, Arrow.class);
            arrow.setVelocity(dir.multiply(1.5));
            arrow.setShooter(skeleton);
        }
    }
}
