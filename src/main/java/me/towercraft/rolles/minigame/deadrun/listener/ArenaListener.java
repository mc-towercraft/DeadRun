package me.towercraft.rolles.minigame.deadrun.listener;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import me.towercraft.rolles.minigame.deadrun.event.SpectatorEvent;
import me.towercraft.rolles.minigame.deadrun.handler.ArenaHandler;
import me.towercraft.rolles.minigame.deadrun.spectator.SpectatorFactory;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlayersDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageSpectatorsDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.config.Config;
import me.towercraft.rolles.minigame.deadrun.util.storage.notification.Sender;
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
import java.util.concurrent.locks.Condition;

public class ArenaListener implements Listener {

    private final SpectatorFactory spectatorFactory;
    private final FileConfiguration config;
    private final Configuration message;

    public ArenaListener() {
        this.spectatorFactory = Deadrun.spectatorFactory;
        this.config = Deadrun.config;
        this.message = Deadrun.message;
    }

    private void teleportOnSpawn(Player player) {
        List<String> locationConfig = Arrays.asList(config.getString("arena.spawn").split(","));
        Location location = new Location(
                player.getWorld(), Double.parseDouble(locationConfig.get(0)),
                Double.parseDouble(locationConfig.get(1)), Double.parseDouble(locationConfig.get(2)),
                Float.parseFloat(locationConfig.get(3)), Float.parseFloat(locationConfig.get(4)));
        player.teleport(location);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!StorageSpectatorsDB.getValue().contains(event.getPlayer())) {
            if (event.getPlayer().getLocation().getY() < 20) {
                Bukkit.getPluginManager().callEvent(new SpectatorEvent(event.getPlayer()));
            } else {
                ArenaHandler.removeBlocks(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN));
            }
        } else {
            if (event.getPlayer().getLocation().getY() < config.getInt("arena.loose-zone")) {
                Sender.messageForListPlayers(message.getString("arena.loose").replace("<name>", event.getPlayer().getName()));
                teleportOnSpawn(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onSpectator(SpectatorEvent event) {
        spectatorFactory.addPlayer(event.getPlayer());
        StorageSpectatorsDB.addValue(event.getPlayer());
        teleportOnSpawn(event.getPlayer());
    }
}
