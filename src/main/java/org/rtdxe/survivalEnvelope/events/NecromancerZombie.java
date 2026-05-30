package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class NecromancerZombie implements Listener {

    private final ConfigManager config;
    private final JavaPlugin plugin;
    private final NamespacedKey necroKey;
    private final NamespacedKey cooldownKey;

    public NecromancerZombie(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.plugin = plugin;
        this.necroKey   = new NamespacedKey(plugin, "necromancer");
        this.cooldownKey = new NamespacedKey(plugin, "necromancer_cooldown");
    }

    @EventHandler
    public void onZombieSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Zombie zombie)) return;
        if (Math.random() >= config.getNecromancerZombieSpawnChance()) return;

        zombie.getPersistentDataContainer().set(necroKey, PersistentDataType.BOOLEAN, true);
        zombie.setCustomName("§5Некромант");
        zombie.setCustomNameVisible(true);
        zombie.getEquipment().setItem(EquipmentSlot.HEAD, ItemStack.of(Material.CARVED_PUMPKIN));
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Monster dead)) return;

        var deathLoc = dead.getLocation();

        for (Entity e : deathLoc.getWorld().getNearbyEntities(deathLoc, 20, 20, 20)) {
            if (!(e instanceof Zombie necro)) continue;
            if (!necro.getPersistentDataContainer().getOrDefault(necroKey, PersistentDataType.BOOLEAN, false)) continue;
            if (necro == dead) continue;

            long lastRevive = necro.getPersistentDataContainer()
                    .getOrDefault(cooldownKey, PersistentDataType.LONG, 0L);
            if (System.currentTimeMillis() - lastRevive < 10_000) continue;

            necro.getPersistentDataContainer().set(cooldownKey, PersistentDataType.LONG, System.currentTimeMillis());
            var type = dead.getType();

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (!necro.isValid() || necro.isDead()) return;
                deathLoc.getWorld().strikeLightningEffect(deathLoc);
                deathLoc.getWorld().spawnEntity(deathLoc, type);
            }, 60L);

            break; // один некромант — один ревайв за смерть
        }
    }
}
