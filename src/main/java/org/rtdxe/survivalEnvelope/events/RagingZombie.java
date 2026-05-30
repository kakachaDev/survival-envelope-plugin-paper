package org.rtdxe.survivalEnvelope.events;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RagingZombie implements Listener {

    private final NamespacedKey rageKey;
    // UUID зомби, уже вошедших в ярость, чтобы не добавлять модификатор повторно
    private final Set<UUID> raging = new HashSet<>();

    public RagingZombie(JavaPlugin plugin) {
        this.rageKey = new NamespacedKey(plugin, "zombie_rage_speed");
    }

    @EventHandler
    public void onZombieDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (raging.contains(zombie.getUniqueId())) return;

        double newHealth = zombie.getHealth() - event.getFinalDamage();
        AttributeInstance maxHealthAttr = zombie.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealthAttr == null || newHealth <= 0) return;

        if (newHealth < maxHealthAttr.getValue() * 0.5) {
            raging.add(zombie.getUniqueId());

            AttributeInstance speed = zombie.getAttribute(Attribute.MOVEMENT_SPEED);
            if (speed != null) {
                // +50% к скорости, не заменяет базовое значение
                speed.addModifier(new AttributeModifier(
                        rageKey,
                        0.5,
                        AttributeModifier.Operation.MULTIPLY_SCALAR_1
                ));
            }
        }
    }

    @EventHandler
    public void onZombieDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            raging.remove(zombie.getUniqueId());
        }
    }
}
