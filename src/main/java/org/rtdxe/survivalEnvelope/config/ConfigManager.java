package org.rtdxe.survivalEnvelope.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private final JavaPlugin plugin;
    private final FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    // Zombie
    public double getZombieNightSpeed() {
        return config.getDouble("mobBehavior.zombieNightSpeed", 0.35);
    }

    public double getZombieDaySpeed() {
        return config.getDouble("mobBehavior.zombieDaySpeed", 0.23);
    }

    public double getZombieJumpChance() {
        return config.getDouble("mobBehavior.zombieJumpChance", 0.15);
    }

    public boolean getZombieJumpOnlyNight() {
        return config.getBoolean("mobBehavior.zombieJumpOnlyNight", true);
    }

    // Creeper
    public double getCreeperJumpChance() {
        return config.getDouble("mobBehavior.creeperJumpChance", 0.75);
    }

    public boolean getCreeperJumpOnlyNight() {
        return config.getBoolean("mobBehavior.creeperJumpOnlyNight", false);
    }

    // Spider
    public double getSpiderJumpChance() {
        return config.getDouble("mobBehavior.spiderJumpChance", 0.15);
    }

    public double getSpiderJumpStrength() {
        return config.getDouble("mobBehavior.spiderJumpStrength", 2.0);
    }

    public boolean getSpiderJumpOnlyNight() {
        return config.getBoolean("mobBehavior.spiderJumpOnlyNight", true);
    }

    // Spawn chances
    public double getMiniCreeperSpawnChance() {
        return config.getDouble("spawn.miniCreeperChance", 0.25);
    }

    public double getBombSkeletonSpawnChance() {
        return config.getDouble("spawn.bombSkeletonChance", 0.25);
    }

    public double getGiantSpiderSpawnChance() {
        return config.getDouble("spawn.giantSpiderChance", 0.10);
    }

    public double getNecromancerZombieSpawnChance() {
        return config.getDouble("spawn.necromancerZombieChance", 0.06);
    }

    public double getSwarmSpiderSpawnChance() {
        return config.getDouble("spawn.swarmSpiderChance", 0.15);
    }

    public double getShieldSkeletonSpawnChance() {
        return config.getDouble("spawn.shieldSkeletonChance", 0.15);
    }

    public double getFireSkeletonSpawnChance() {
        return config.getDouble("spawn.fireSkeletonChance", 0.15);
    }

    public double getPackLeaderSpawnChance() {
        return config.getDouble("spawn.packLeaderChance", 0.08);
    }

    public double getSiegeZombieSpawnChance() {
        return config.getDouble("spawn.siegeZombieChance", 0.05);
    }

    public double getCurseEndermanSpawnChance() {
        return config.getDouble("spawn.curseEndermanChance", 0.10);
    }

    public double getStonebackZombieSpawnChance() {
        return config.getDouble("spawn.stonebackZombieChance", 0.08);
    }

    public double getGraveDiggerSpawnChance() {
        return config.getDouble("spawn.gravediggerZombieChance", 0.06);
    }

    public double getMirrorSkeletonSpawnChance() {
        return config.getDouble("spawn.mirrorSkeletonChance", 0.10);
    }

    public double getBoneburstSkeletonSpawnChance() {
        return config.getDouble("spawn.boneburstSkeletonChance", 0.08);
    }

    public double getMagneticCreeperSpawnChance() {
        return config.getDouble("spawn.magneticCreeperChance", 0.08);
    }

    public double getAbyssalSpiderSpawnChance() {
        return config.getDouble("spawn.abyssalSpiderChance", 0.08);
    }

    public double getCorruptedGolemSpawnChance() {
        return config.getDouble("spawn.corruptedGolemChance", 0.02);
    }

    // Player
    public double getDropMainhandItemOnDamageChance() {
        return config.getDouble("player.dropMainhandItemOnDamageChance", 0.08);
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
    }
}
