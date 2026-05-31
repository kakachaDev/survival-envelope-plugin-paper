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
        new SkeletonWeaponTask(config, this);
        new PackLeaderTask(this);
        new SiegeZombieTask(this, tracker);
        new GraveDiggerTask(this);
        new MagneticCreeperTask(this);
        new AbyssalSpiderTask(this);
        new CorruptedGolemTask(this);
        new MobParticleTask(this);

        // Events — базовые
        getServer().getPluginManager().registerEvents(new MiniCreeper(config, this), this);
        getServer().getPluginManager().registerEvents(new BombSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new DropItemOnDamage(config), this);
        getServer().getPluginManager().registerEvents(new GiantSpider(config), this);
        getServer().getPluginManager().registerEvents(new RagingZombie(config, this), this);

        // Events — новые
        getServer().getPluginManager().registerEvents(new NecromancerZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new SwarmSpider(config, this), this);
        getServer().getPluginManager().registerEvents(new ShieldSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new FireSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new PackLeaderZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new SiegeZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new CurseEnderman(config, this), this);
        getServer().getPluginManager().registerEvents(new StonebackZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new GraveDiggerZombie(config, this), this);
        getServer().getPluginManager().registerEvents(new MirrorSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new BoneburstSkeleton(config, this), this);
        getServer().getPluginManager().registerEvents(new MagneticCreeper(config, this), this);
        getServer().getPluginManager().registerEvents(new AbyssalSpider(config, this), this);
        getServer().getPluginManager().registerEvents(new CorruptedGolem(config, this), this);

        // /se reload
        var cmd = getCommand("se");
        if (cmd != null) {
            cmd.setExecutor((sender, command, label, args) -> {
                if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    sender.sendMessage("§aSurvivalEnvelope: config reloaded.");
                    return true;
                }
                sender.sendMessage("§cUsage: /se reload");
                return true;
            });
        }

        getLogger().info("SurvivalEnvelope enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SurvivalEnvelope disabled!");
    }
}
