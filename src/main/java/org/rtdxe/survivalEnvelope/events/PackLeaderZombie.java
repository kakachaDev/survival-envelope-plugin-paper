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

public class PackLeaderZombie implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public PackLeaderZombie(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "pack_leader");
    }

    @EventHandler
    public void onZombieSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (zombie instanceof ZombieVillager) return;
        if (Math.random() >= config.getPackLeaderSpawnChance()) return;

        zombie.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        zombie.setCustomName("§4Вожак стаи");
        zombie.setCustomNameVisible(true);

        var scale = zombie.getAttribute(Attribute.SCALE);
        if (scale != null) scale.setBaseValue(1.3);

        var health = zombie.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(health.getBaseValue() + 6);
            zombie.setHealth(health.getValue());
        }
    }
}
