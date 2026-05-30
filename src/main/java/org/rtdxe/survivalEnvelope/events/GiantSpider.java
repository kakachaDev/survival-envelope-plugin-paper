package org.rtdxe.survivalEnvelope.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class GiantSpider implements Listener {

    private final ConfigManager config;

    public GiantSpider(ConfigManager config) {
        this.config = config;
    }

    @EventHandler
    public void onSpiderSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Spider spider)) return;
        if (spider instanceof CaveSpider) return;
        if (Math.random() >= config.getGiantSpiderSpawnChance()) return;

        var scale = spider.getAttribute(Attribute.SCALE);
        if (scale != null) scale.setBaseValue(2.0);

        var health = spider.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(health.getBaseValue() * 2);
            spider.setHealth(health.getValue());
        }

        // Чуть медленнее за размер
        var speed = spider.getAttribute(Attribute.MOVEMENT_SPEED);
        if (speed != null) speed.setBaseValue(speed.getBaseValue() * 0.8);

    }
}
