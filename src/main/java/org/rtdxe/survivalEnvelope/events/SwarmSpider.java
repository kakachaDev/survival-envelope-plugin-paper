package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

import java.util.Random;

public class SwarmSpider implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;
    private final Random random = new Random();

    public SwarmSpider(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "swarm_spider");
    }

    @EventHandler
    public void onSpiderSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Spider spider)) return;
        if (spider instanceof CaveSpider) return;
        if (Math.random() >= config.getSwarmSpiderSpawnChance()) return;

        spider.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        spider.setCustomName("§8Паук-матка");
        spider.setCustomNameVisible(true);
    }

    @EventHandler
    public void onSpiderDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Spider spider)) return;
        if (!spider.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        Location loc = spider.getLocation();
        int count = 2 + random.nextInt(2); // 2 или 3
        for (int i = 0; i < count; i++) {
            loc.getWorld().spawnEntity(loc, EntityType.CAVE_SPIDER);
        }
    }
}
