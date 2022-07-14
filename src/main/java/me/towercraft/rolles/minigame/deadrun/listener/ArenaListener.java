package me.towercraft.rolles.minigame.deadrun.listener;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import me.towercraft.rolles.minigame.deadrun.event.GameStopEvent;
import me.towercraft.rolles.minigame.deadrun.event.PlayerLooseEvent;
import me.towercraft.rolles.minigame.deadrun.event.SpectatorEvent;
import me.towercraft.rolles.minigame.deadrun.handler.ArenaHandler;
import me.towercraft.rolles.minigame.deadrun.spectator.SpectatorFactory;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlacesDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlayersCounterDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageSpectatorsDB;
import me.towercraft.rolles.minigame.deadrun.util.config.Config;
import me.towercraft.rolles.minigame.deadrun.util.notification.Sender;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.List;

public class ArenaListener implements Listener {

    private final SpectatorFactory spectatorFactory;
    private final FileConfiguration config;
    private final Configuration message;

    public ArenaListener() {
        this.spectatorFactory = Deadrun.spectatorFactory;
        this.config = Deadrun.config;
        this.message = Deadrun.message;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!StorageSpectatorsDB.getValue().contains(event.getPlayer())) {
            if (event.getPlayer().getLocation().getY() < config.getInt("arena.loose-zone")) {
                Sender.messageForListPlayers(message.getString("arena.loose").replace("<name>", event.getPlayer().getName()));
                Bukkit.getPluginManager().callEvent(new PlayerLooseEvent(event.getPlayer()));
            } else {
                ArenaHandler.removeBlocks(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN));
            }
        } else {
            if (event.getPlayer().getLocation().getY() < config.getInt("arena.loose-zone")) {
                Sender.teleportOnSpawn(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onLoose(PlayerLooseEvent event) {
        Bukkit.getPluginManager().callEvent(new SpectatorEvent(event.getPlayer()));
        if(StoragePlayersCounterDB.getValue() <= 3) {
            StoragePlacesDB.addPlayer(event.getPlayer());
        }
        if (StoragePlayersCounterDB.getValue() == 1) {
            StoragePlacesDB.addPlayer(event.getPlayer());
            Bukkit.getPluginManager().callEvent(new GameStopEvent());
        }
    }

    @EventHandler
    public void onSpectator(SpectatorEvent event) {
        spectatorFactory.addPlayer(event.getPlayer());
        StorageSpectatorsDB.addValue(event.getPlayer());
        Sender.teleportOnSpawn(event.getPlayer());
    }
}
