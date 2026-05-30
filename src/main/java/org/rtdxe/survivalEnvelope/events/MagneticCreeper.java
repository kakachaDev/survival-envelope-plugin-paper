package org.rtdxe.survivalEnvelope.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class MagneticCreeper implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public MagneticCreeper(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "magnetic_creeper");
    }

    @EventHandler
    public void onCreeperSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Creeper creeper)) return;
        if (Math.random() >= config.getMagneticCreeperSpawnChance()) return;

        creeper.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        creeper.setMaxFuseTicks(100); // 5 секунд втягивания
    }
}
