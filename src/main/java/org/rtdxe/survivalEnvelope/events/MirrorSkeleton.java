package org.rtdxe.survivalEnvelope.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class MirrorSkeleton implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public MirrorSkeleton(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "mirror_skeleton");
    }

    @EventHandler
    public void onSkeletonSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (Math.random() >= config.getMirrorSkeletonSpawnChance()) return;

        skeleton.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (!skeleton.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;
        if (!(event.getDamager() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Player player)) return;

        event.setCancelled(true);
        arrow.remove();

        Vector dir = player.getEyeLocation().toVector()
                .subtract(skeleton.getEyeLocation().toVector())
                .normalize();
        Arrow reflected = skeleton.launchProjectile(Arrow.class);
        reflected.setVelocity(dir.multiply(2.5));
    }
}
