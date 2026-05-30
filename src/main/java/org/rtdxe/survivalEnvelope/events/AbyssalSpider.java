package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class AbyssalSpider implements Listener {

    private final ConfigManager config;
    private final NamespacedKey spiderKey;
    private final NamespacedKey webKey;
    private final JavaPlugin plugin;

    public AbyssalSpider(ConfigManager config, JavaPlugin plugin) {
        this.config = config;
        this.plugin = plugin;
        this.spiderKey = new NamespacedKey(plugin, "abyssal_spider");
        this.webKey    = new NamespacedKey(plugin, "abyssal_web");
    }

    @EventHandler
    public void onSpiderSpawn(CreatureSpawnEvent event) {
        if (!(event.getEntity() instanceof Spider spider)) return;
        if (spider instanceof CaveSpider) return;
        if (Math.random() >= config.getAbyssalSpiderSpawnChance()) return;

        spider.getPersistentDataContainer().set(spiderKey, PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onWebHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball snowball)) return;
        if (!snowball.getPersistentDataContainer().getOrDefault(webKey, PersistentDataType.BOOLEAN, false)) return;
        if (!(snowball.getShooter() instanceof Spider spider)) return;

        if (event.getHitEntity() instanceof Player player) {
            Vector pull = spider.getLocation().toVector()
                    .subtract(player.getLocation().toVector())
                    .normalize()
                    .multiply(1.2);
            player.setVelocity(pull);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 1, false, true));
        } else if (event.getHitBlock() != null && event.getHitBlockFace() != null) {
            Block target = event.getHitBlock().getRelative(event.getHitBlockFace());
            if (target.getType() == Material.AIR) {
                target.setType(Material.COBWEB);
                new BukkitRunnable() {
                    @Override public void run() {
                        if (target.getType() == Material.COBWEB) target.setType(Material.AIR);
                    }
                }.runTaskLater(plugin, 80L);
            }
        }
    }
}
