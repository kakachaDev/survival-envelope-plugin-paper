package org.rtdxe.survivalEnvelope.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.rtdxe.survivalEnvelope.config.ConfigManager;

import java.util.Random;

public class DropItemOnDamage implements Listener {

    private final ConfigManager config;
    private final Random random = new Random();

    public DropItemOnDamage(ConfigManager config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerDamaged(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // проверяем, что атакующий — моб
        if (!(event.getDamager() instanceof LivingEntity damager)) return;
        if (damager.getType() == EntityType.PLAYER) return; // исключаем PvP, если не надо

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null || itemInHand.getType() == Material.AIR) return;

        // проверяем шанс
        if (random.nextDouble() <= config.getDropMainhandItemOnDamageChance()) {
            // выбиваем предмет
            player.getInventory().setItemInMainHand(ItemStack.empty());
            player.getWorld().dropItemNaturally(player.getLocation(), itemInHand);

            // можно добавить сообщение игроку
            player.sendMessage("§cМоб выбил у тебя оружие!");
        }
    }
}
