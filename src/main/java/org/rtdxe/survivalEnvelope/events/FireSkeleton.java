package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class FireSkeleton implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public FireSkeleton(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "fire_skeleton");
    }

    @EventHandler
    public void onSkeletonSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (Math.random() >= config.getFireSkeletonSpawnChance()) return;

        skeleton.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        // Постоянный Fire Resistance — скелет не горит
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
    }

    @EventHandler
    public void onSkeletonShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Skeleton skeleton)) return;
        if (!skeleton.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        if (event.getProjectile() instanceof Arrow arrow) {
            arrow.setFireTicks(400); // стрела горит ~20 секунд в полёте
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (!(arrow.getShooter() instanceof Skeleton skeleton)) return;
        if (!skeleton.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        Block hitBlock = event.getHitBlock();
        BlockFace hitFace = event.getHitBlockFace();
        if (hitBlock == null || hitFace == null) return;

        if (Math.random() < 0.10) {
            Block fireTarget = hitBlock.getRelative(hitFace);
            if (fireTarget.getType() == Material.AIR) {
                fireTarget.setType(Material.FIRE);
            }
        }
    }
}
