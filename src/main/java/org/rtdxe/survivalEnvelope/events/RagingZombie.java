package org.rtdxe.survivalEnvelope.events;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class RagingZombie implements Listener {

    private final NamespacedKey rageKey;
    private final NamespacedKey ragingKey;
    private final org.rtdxe.survivalEnvelope.config.ConfigManager config;

    public RagingZombie(org.rtdxe.survivalEnvelope.config.ConfigManager config, JavaPlugin plugin) {
        this.config    = config;
        this.rageKey   = new NamespacedKey(plugin, "zombie_rage_speed");
        this.ragingKey = new NamespacedKey(plugin, "raging");
    }

    @EventHandler
    public void onZombieDamage(EntityDamageEvent event) {
        if (!config.isRagingZombieEnabled()) return;
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (zombie.getPersistentDataContainer().has(ragingKey, PersistentDataType.BOOLEAN)) return;

        double newHealth = zombie.getHealth() - event.getFinalDamage();
        AttributeInstance maxHp = zombie.getAttribute(Attribute.MAX_HEALTH);
        if (maxHp == null || newHealth <= 0) return;

        if (newHealth < maxHp.getValue() * 0.5) {
            zombie.getPersistentDataContainer().set(ragingKey, PersistentDataType.BOOLEAN, true);

            AttributeInstance speed = zombie.getAttribute(Attribute.MOVEMENT_SPEED);
            if (speed != null) {
                speed.addModifier(new AttributeModifier(
                        rageKey, 0.5, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
            }
        }
    }
}
