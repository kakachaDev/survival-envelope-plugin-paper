package org.rtdxe.survivalEnvelope.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MobParticleTask {

    public MobParticleTask(JavaPlugin plugin) {
        // Zombie variants
        NamespacedKey stonebackKey  = new NamespacedKey(plugin, "stoneback_zombie");
        NamespacedKey gravediggerKey= new NamespacedKey(plugin, "grave_digger");
        NamespacedKey necroKey      = new NamespacedKey(plugin, "necromancer");
        NamespacedKey packKey       = new NamespacedKey(plugin, "pack_leader");
        NamespacedKey siegeKey      = new NamespacedKey(plugin, "siege_zombie");
        // Skeleton variants
        NamespacedKey mirrorKey     = new NamespacedKey(plugin, "mirror_skeleton");
        NamespacedKey boneburstKey  = new NamespacedKey(plugin, "boneburst_skeleton");
        // Creeper
        NamespacedKey magneticKey   = new NamespacedKey(plugin, "magnetic_creeper");
        // Spider
        NamespacedKey abyssalKey    = new NamespacedKey(plugin, "abyssal_spider");
        NamespacedKey swarmKey      = new NamespacedKey(plugin, "swarm_spider");
        // Golem / Enderman
        NamespacedKey golemKey      = new NamespacedKey(plugin, "corrupted_golem");
        NamespacedKey curseKey      = new NamespacedKey(plugin, "curse_enderman");

        BlockData dirtData  = Material.DIRT.createBlockData();
        var pdc = PersistentDataType.BOOLEAN;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (var world : Bukkit.getWorlds()) {

                    for (var z : world.getEntitiesByClass(Zombie.class)) {
                        var pd  = z.getPersistentDataContainer();
                        var loc = z.getLocation().add(0, 1, 0);
                        if (pd.getOrDefault(stonebackKey, pdc, false)) {
                            // Серый дым — каменная броня
                            world.spawnParticle(Particle.SMOKE, loc, 3, 0.3, 0.3, 0.3, 0.01);
                        } else if (pd.getOrDefault(gravediggerKey, pdc, false)) {
                            // Земляная пыль у ног
                            world.spawnParticle(Particle.BLOCK, z.getLocation().add(0, 0.1, 0), 4, 0.3, 0.1, 0.3, 0, dirtData);
                        } else if (pd.getOrDefault(necroKey, pdc, false)) {
                            // Фиолетовые капли — ведьмовская магия
                            world.spawnParticle(Particle.WITCH, loc, 4, 0.3, 0.4, 0.3, 0);
                        } else if (pd.getOrDefault(packKey, pdc, false)) {
                            // Зелёные искры — даёт бафф союзникам
                            world.spawnParticle(Particle.HAPPY_VILLAGER, loc, 2, 0.4, 0.3, 0.4, 0);
                        } else if (pd.getOrDefault(siegeKey, pdc, false)) {
                            // Большой дым — тяжёлый осадный моб
                            world.spawnParticle(Particle.LARGE_SMOKE, loc, 2, 0.3, 0.3, 0.3, 0.01);
                        }
                    }

                    for (var sk : world.getEntitiesByClass(Skeleton.class)) {
                        var pd  = sk.getPersistentDataContainer();
                        var loc = sk.getLocation().add(0, 1, 0);
                        if (pd.getOrDefault(mirrorKey, pdc, false)) {
                            // Белые искры — отражает всё
                            world.spawnParticle(Particle.END_ROD, loc, 3, 0.2, 0.4, 0.2, 0.02);
                        } else if (pd.getOrDefault(boneburstKey, pdc, false)) {
                            // Оранжевые криты — взорвётся стрелами
                            world.spawnParticle(Particle.CRIT, loc, 2, 0.2, 0.3, 0.2, 0.01);
                        }
                    }

                    for (var cr : world.getEntitiesByClass(Creeper.class)) {
                        if (!cr.getPersistentDataContainer().getOrDefault(magneticKey, pdc, false)) continue;
                        // Электрические искры — всегда видно, интенсивнее при зарядке (обрабатывается MagneticCreeperTask)
                        world.spawnParticle(Particle.ELECTRIC_SPARK, cr.getLocation().add(0, 0.8, 0), 2, 0.2, 0.3, 0.2, 0);
                    }

                    for (var sp : world.getEntitiesByClass(Spider.class)) {
                        var pd  = sp.getPersistentDataContainer();
                        var loc = sp.getLocation().add(0, 0.5, 0);
                        if (pd.getOrDefault(abyssalKey, pdc, false)) {
                            // Капли воды — паутина/засасывание
                            world.spawnParticle(Particle.DRIPPING_WATER, loc, 3, 0.4, 0.2, 0.4, 0);
                        } else if (pd.getOrDefault(swarmKey, pdc, false)) {
                            // Хлопок — рой насекомых
                            world.spawnParticle(Particle.POOF, loc, 2, 0.3, 0.2, 0.3, 0.01);
                        }
                    }

                    for (var g : world.getEntitiesByClass(IronGolem.class)) {
                        if (!g.getPersistentDataContainer().getOrDefault(golemKey, pdc, false)) continue;
                        // Огонь — коррумпированный, горит постоянно
                        world.spawnParticle(Particle.FLAME, g.getLocation().add(0, 1.5, 0), 5, 0.4, 0.6, 0.4, 0.02);
                    }

                    for (var e : world.getEntitiesByClass(Enderman.class)) {
                        if (!e.getPersistentDataContainer().getOrDefault(curseKey, pdc, false)) continue;
                        // Порталные частицы — тёмная магия
                        world.spawnParticle(Particle.PORTAL, e.getLocation().add(0, 1, 0), 8, 0.3, 0.5, 0.3, 0.5);
                    }
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }
}
