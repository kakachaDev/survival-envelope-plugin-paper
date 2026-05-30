package org.rtdxe.survivalEnvelope.events;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class SiegeZombie implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public SiegeZombie(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "siege_zombie");
    }

    @EventHandler
    public void onZombieSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (zombie instanceof ZombieVillager) return;
        if (Math.random() >= config.getSiegeZombieSpawnChance()) return;

        zombie.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

        var scale = zombie.getAttribute(Attribute.SCALE);
        if (scale != null) scale.setBaseValue(1.4);

        var health = zombie.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(health.getBaseValue() + 10);
            zombie.setHealth(health.getValue());
        }

        var speed = zombie.getAttribute(Attribute.MOVEMENT_SPEED);
        if (speed != null) speed.setBaseValue(speed.getBaseValue() * 0.7);
    }
}
