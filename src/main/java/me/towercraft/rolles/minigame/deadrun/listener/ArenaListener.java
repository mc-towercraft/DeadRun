package me.towercraft.rolles.minigame.deadrun.listener;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import me.towercraft.rolles.minigame.deadrun.event.SpectatorEvent;
import me.towercraft.rolles.minigame.deadrun.handler.ArenaHandler;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlayersDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageSpectatorsDB;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ArenaListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!StorageSpectatorsDB.getValue().contains(event.getPlayer())) {
            if (event.getPlayer().getLocation().getY() < 20) {
                Bukkit.getPluginManager().callEvent(new SpectatorEvent(event.getPlayer()));
            } else {
                ArenaHandler.removeBlocks(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN));
            }
        } else {
            if (event.getPlayer().getLocation().getY() < 20) {
                // Телепорт на спавн арены event.getPlayer().teleport();
            }
        }
    }

    @EventHandler
    public void onSpectator(SpectatorEvent event) {
        Deadrun.spectatorFactory.addPlayer(event.getPlayer());
        StorageSpectatorsDB.addValue(event.getPlayer());
        // Телепорт на спавн арены event.getPlayer().teleport();
    }
}
