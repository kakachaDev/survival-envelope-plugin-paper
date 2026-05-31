package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

public class ZombieBehaviorTask {

    public ZombieBehaviorTask(ConfigManager config, JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    long time = world.getTime();
                    boolean isNight = time >= 13000 && time <= 23000;

                    world.getEntitiesByClass(Zombie.class).forEach(zombie -> {
                        if (config.isZombieSpeedEnabled()) {
                            AttributeInstance speed = zombie.getAttribute(Attribute.MOVEMENT_SPEED);
                            if (speed != null) {
                                speed.setBaseValue(isNight ? config.getZombieNightSpeed() : config.getZombieDaySpeed());
                            }
                        }

                        if (!config.isZombieJumpEnabled()) return;
                        if (config.getZombieJumpOnlyNight() && !isNight) return;

                        Player target = world.getNearbyEntities(zombie.getLocation(), 10, 5, 10).stream()
                                .filter(e -> e instanceof Player p && p.getGameMode() == GameMode.SURVIVAL)
                                .map(e -> (Player) e)
                                .findFirst()
                                .orElse(null);

                        if (target != null && zombie.hasLineOfSight(target) && Math.random() <= config.getZombieJumpChance()) {
                            Vector direction = target.getLocation().toVector()
                                    .subtract(zombie.getLocation().toVector())
                                    .normalize()
                                    .multiply(0.6);
                            direction.setY(0.5);
                            zombie.setVelocity(direction);
                        }
                    });
                }
            }
        }.runTaskTimer(plugin, 20L, 40L);
    }
}
