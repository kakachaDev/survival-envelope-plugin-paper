package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class CorruptedGolem implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public CorruptedGolem(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "corrupted_golem");
    }

    @EventHandler
    public void onGolemSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof IronGolem golem)) return;
        if (golem.isPlayerCreated()) return;
        if (Math.random() >= config.getCorruptedGolemSpawnChance()) return;

        golem.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

        var health = golem.getAttribute(Attribute.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(150);
            golem.setHealth(150);
        }

        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chest.getItemMeta();
        meta.setColor(Color.fromRGB(180, 20, 20));
        chest.setItemMeta(meta);
        golem.getEquipment().setChestplate(chest);
        golem.getEquipment().setChestplateDropChance(0f);
    }
}
