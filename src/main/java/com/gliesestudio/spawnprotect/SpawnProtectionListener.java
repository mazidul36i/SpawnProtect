package com.gliesestudio.spawnprotect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnProtectionListener implements Listener {

    private final Config config;

    public SpawnProtectionListener(SpawnProtect plugin) {
        config = plugin.config;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        if (isInSpawnArea(blockLocation) && !event.getPlayer().isOp()) {
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        if (isInSpawnArea(blockLocation) && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Location explosionLocation = event.getLocation();
        if (isInSpawnArea(explosionLocation)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        Location explosionLocation = event.getBlock().getLocation();
        if (isInSpawnArea(explosionLocation)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Location interactLocation = event.getClickedBlock() != null ? event.getClickedBlock().getLocation() : event.getPlayer().getLocation();
        if (isInSpawnArea(interactLocation) && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }

    private boolean isInSpawnArea(Location location) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        SpawnPosition spawnPosition = config.getSpawnPosition();
        return x >= spawnPosition.getX1() && x <= spawnPosition.getX2() && z >= spawnPosition.getZ1() && z <= spawnPosition.getZ2();
    }
}
