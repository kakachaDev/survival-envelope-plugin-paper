package org.rtdxe.survivalEnvelope.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class MiniCreeper implements Listener {
    private final ConfigManager config;

    public MiniCreeper(ConfigManager config) {
        this.config = config;
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

        creeper.setMaxFuseTicks(10);
        creeper.setExplosionRadius(2);
        creeper.setIgnited(false);
    }

}
