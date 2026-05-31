package org.rtdxe.survivalEnvelope.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    private FileConfiguration cfg() {
        return plugin.getConfig(); // always fresh — works after /se reload
    }

    // ── Zombie behavior ──────────────────────────────────────────────────────
    public boolean isZombieSpeedEnabled()    { return cfg().getBoolean("mobBehavior.zombieSpeedEnabled", true); }
    public double  getZombieNightSpeed()     { return cfg().getDouble ("mobBehavior.zombieNightSpeed",   0.35); }
    public double  getZombieDaySpeed()       { return cfg().getDouble ("mobBehavior.zombieDaySpeed",     0.23); }
    public boolean isZombieJumpEnabled()     { return cfg().getBoolean("mobBehavior.zombieJumpEnabled",  true); }
    public double  getZombieJumpChance()     { return cfg().getDouble ("mobBehavior.zombieJumpChance",   0.15); }
    public boolean getZombieJumpOnlyNight()  { return cfg().getBoolean("mobBehavior.zombieJumpOnlyNight",true); }
    public boolean isRagingZombieEnabled()   { return cfg().getBoolean("mobBehavior.ragingZombieEnabled",true); }

    // ── Creeper behavior ─────────────────────────────────────────────────────
    public double  getCreeperJumpChance()     { return cfg().getDouble ("mobBehavior.creeperJumpChance",    0.75); }
    public boolean getCreeperJumpOnlyNight()  { return cfg().getBoolean("mobBehavior.creeperJumpOnlyNight", false); }

    // ── Spider behavior ──────────────────────────────────────────────────────
    public boolean isSpiderJumpEnabled()     { return cfg().getBoolean("mobBehavior.spiderJumpEnabled",  true); }
    public double  getSpiderJumpChance()     { return cfg().getDouble ("mobBehavior.spiderJumpChance",   0.15); }
    public double  getSpiderJumpStrength()   { return cfg().getDouble ("mobBehavior.spiderJumpStrength", 2.0);  }
    public boolean getSpiderJumpOnlyNight()  { return cfg().getBoolean("mobBehavior.spiderJumpOnlyNight",true); }

    // ── Skeleton behavior ────────────────────────────────────────────────────
    public boolean isSkeletonWeaponSwitchEnabled() { return cfg().getBoolean("mobBehavior.skeletonWeaponSwitchEnabled", true); }

    // ── Player ───────────────────────────────────────────────────────────────
    public boolean isItemKnockoutEnabled()            { return cfg().getBoolean("player.itemKnockoutEnabled",           true); }
    public double  getDropMainhandItemOnDamageChance() { return cfg().getDouble ("player.dropMainhandItemOnDamageChance", 0.08); }

    // ── Spawn chances ────────────────────────────────────────────────────────
    // Returns 0.0 when disabled → Math.random() >= 0.0 is always true → mob never spawns
    private double chance(String enabledKey, String chanceKey, double def) {
        if (!cfg().getBoolean(enabledKey, true)) return 0.0;
        return cfg().getDouble(chanceKey, def);
    }

    public double getMiniCreeperSpawnChance()       { return chance("spawn.miniCreeperEnabled",       "spawn.miniCreeperChance",       0.25); }
    public double getBombSkeletonSpawnChance()       { return chance("spawn.bombSkeletonEnabled",      "spawn.bombSkeletonChance",      0.25); }
    public double getGiantSpiderSpawnChance()        { return chance("spawn.giantSpiderEnabled",       "spawn.giantSpiderChance",       0.10); }
    public double getNecromancerZombieSpawnChance()  { return chance("spawn.necromancerZombieEnabled", "spawn.necromancerZombieChance", 0.06); }
    public double getSwarmSpiderSpawnChance()        { return chance("spawn.swarmSpiderEnabled",       "spawn.swarmSpiderChance",       0.15); }
    public double getShieldSkeletonSpawnChance()     { return chance("spawn.shieldSkeletonEnabled",    "spawn.shieldSkeletonChance",    0.15); }
    public double getFireSkeletonSpawnChance()       { return chance("spawn.fireSkeletonEnabled",      "spawn.fireSkeletonChance",      0.15); }
    public double getPackLeaderSpawnChance()         { return chance("spawn.packLeaderEnabled",        "spawn.packLeaderChance",        0.08); }
    public double getSiegeZombieSpawnChance()        { return chance("spawn.siegeZombieEnabled",       "spawn.siegeZombieChance",       0.05); }
    public double getCurseEndermanSpawnChance()      { return chance("spawn.curseEndermanEnabled",     "spawn.curseEndermanChance",     0.10); }
    public double getStonebackZombieSpawnChance()    { return chance("spawn.stonebackZombieEnabled",   "spawn.stonebackZombieChance",   0.08); }
    public double getGraveDiggerSpawnChance()        { return chance("spawn.gravediggerZombieEnabled", "spawn.gravediggerZombieChance", 0.06); }
    public double getMirrorSkeletonSpawnChance()     { return chance("spawn.mirrorSkeletonEnabled",    "spawn.mirrorSkeletonChance",    0.10); }
    public double getBoneburstSkeletonSpawnChance()  { return chance("spawn.boneburstSkeletonEnabled", "spawn.boneburstSkeletonChance", 0.08); }
    public double getMagneticCreeperSpawnChance()    { return chance("spawn.magneticCreeperEnabled",   "spawn.magneticCreeperChance",   0.08); }
    public double getAbyssalSpiderSpawnChance()      { return chance("spawn.abyssalSpiderEnabled",     "spawn.abyssalSpiderChance",     0.08); }
    public double getCorruptedGolemSpawnChance()     { return chance("spawn.corruptedGolemEnabled",    "spawn.corruptedGolemChance",    0.02); }
}
