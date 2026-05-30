package org.rtdxe.survivalEnvelope.events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class CurseEnderman implements Listener {

    private final ConfigManager config;
    private final NamespacedKey key;

    public CurseEnderman(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.key = new NamespacedKey(plugin, "curse_enderman");
    }

    @EventHandler
    public void onEndermanSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Enderman enderman)) return;
        if (Math.random() >= config.getCurseEndermanSpawnChance()) return;

        enderman.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onEndermanAttack(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!(event.getDamager() instanceof Enderman enderman)) return;
        if (!enderman.getPersistentDataContainer().getOrDefault(key, PersistentDataType.BOOLEAN, false)) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0, false, false)); // 4s
    }
}
