package org.rtdxe.survivalEnvelope.events;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class MiniCreeper implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public MiniCreeper(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "mini_creeper");
    }

    @EventHandler
    public void onCreeperSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Creeper creeper)) return;
        if (Math.random() >= config.getMiniCreeperSpawnChance()) return;

        AttributeInstance scale = creeper.getAttribute(Attribute.SCALE);
        if (scale != null) scale.setBaseValue(0.5);

        var speed = creeper.getAttribute(Attribute.MOVEMENT_SPEED);
        if (speed != null) speed.setBaseValue(speed.getBaseValue() * 1.5);

        var health = creeper.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) health.setBaseValue(2);

        creeper.setMaxFuseTicks(20);
        creeper.setExplosionRadius(1);
        creeper.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof Creeper creeper)) return;
        if (!creeper.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        // Отменяем стандартный взрыв и заменяем слабым (power 0.5 ≈ хлопушка)
        event.setCancelled(true);
        event.getLocation().getWorld().createExplosion(event.getLocation(), 0.5f, false, false, creeper);
    }
}
