package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class GraveDiggerZombie implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public GraveDiggerZombie(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "grave_digger");
    }

    @EventHandler
    public void onZombieSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (zombie instanceof ZombieVillager) return;
        if (Math.random() >= config.getGraveDiggerSpawnChance()) return;

        zombie.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SHOVEL));
        zombie.getEquipment().setItemInMainHandDropChance(0f);
    }
}
