package org.rtdxe.survivalEnvelope;

import org.bukkit.plugin.java.JavaPlugin;
import org.rtdxe.survivalEnvelope.config.ConfigManager;
import org.rtdxe.survivalEnvelope.events.*;
import org.rtdxe.survivalEnvelope.tasks.*;
import org.rtdxe.survivalEnvelope.util.BlockTracker;

public class SurvivalEnvelope extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager config = new ConfigManager(this);

        // Трекер блоков, поставленных игроками (нужен SiegeZombieTask)
        BlockTracker tracker = new BlockTracker();
        getServer().getPluginManager().registerEvents(tracker, this);

        // Tasks
        new ZombieBehaviorTask(config, this);
        new SpiderJumpTask(config, this);
        new SkeletonWeaponTask(this);
        new PackLeaderTask(this);
        new SiegeZombieTask(this, tracker);

        // Events — базовые
        getServer().getPluginManager().registerEvents(new MiniCreeper(config), this);
        getServer().getPluginManager().registerEvents(new BombSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new DropItemOnDamage(config), this);
        getServer().getPluginManager().registerEvents(new GiantSpider(config), this);
        getServer().getPluginManager().registerEvents(new RagingZombie(this), this);

        // Events — новые
        getServer().getPluginManager().registerEvents(new NecromancerZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new SwarmSpider(config, this), this);
        getServer().getPluginManager().registerEvents(new ShieldSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new FireSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new PackLeaderZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new SiegeZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new CurseEnderman(config, this), this);

        getLogger().info("SurvivalEnvelope включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SurvivalEnvelope выключен!");
    }
}
