package org.rtdxe.survivalEnvelope.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockTracker implements Listener {

    private final Set<Location> placed = new HashSet<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        placed.add(event.getBlock().getLocation());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        placed.remove(event.getBlock().getLocation());
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        placed.remove(event.getBlock().getLocation());
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().forEach(b -> placed.remove(b.getLocation()));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().forEach(b -> placed.remove(b.getLocation()));
    }

    public boolean isPlayerPlaced(Block block) {
        return placed.contains(block.getLocation());
    }

    public void remove(Location loc) {
        placed.remove(loc);
    }
}
