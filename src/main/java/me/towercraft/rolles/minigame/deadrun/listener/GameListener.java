package me.towercraft.rolles.minigame.deadrun.listener;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import me.towercraft.rolles.minigame.deadrun.afk.AFKCheck;
import me.towercraft.rolles.minigame.deadrun.enumerate.GameState;
import me.towercraft.rolles.minigame.deadrun.event.GameStartEvent;
import me.towercraft.rolles.minigame.deadrun.event.GameStopEvent;
import me.towercraft.rolles.minigame.deadrun.handler.ArenaHandler;
import me.towercraft.rolles.minigame.deadrun.util.notification.Sender;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageGameStateDB;

import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlacesDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlayersCounterDB;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {

    private final Configuration message;
    private final FileConfiguration config;

    public GameListener() {
        this.message = Deadrun.message;
        this.config = Deadrun.config;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (StorageGameStateDB.getState() == GameState.WAITING) {
            StoragePlayersCounterDB.incrementValue();
            Sender.messageForListPlayers(message.getString("arena.join")
                    .replace("<name>", event.getPlayer().getName())
                    .replace("<current>", String.valueOf(StoragePlayersCounterDB.getValue()))
                    .replace("<max>", config.getString("arena.max-players")));
            Sender.teleportOnSpawn(event.getPlayer());
            if (StoragePlayersCounterDB.getValue() == config.getInt("arena.max-players")) {
                StorageGameStateDB.setState(GameState.STARTING);
                ArenaHandler.timer();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        StoragePlayersCounterDB.decrementValue();
        Sender.messageForListPlayers(message.getString("arena.leave")
                .replace("<name>", event.getPlayer().getName())
                .replace("<current>", String.valueOf(StoragePlayersCounterDB.getValue()))
                .replace("<max>", config.getString("arena.max-players")));
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        StorageGameStateDB.setState(GameState.PLAYING);
        Bukkit.getPluginManager().registerEvents(new ArenaListener(), Deadrun.plugin);
        Deadrun.afkCheck = new AFKCheck();
    }

    @EventHandler
    public void onGameStop(GameStopEvent event) {
        StorageGameStateDB.setState(GameState.RESTARTING);
        Sender.messageForListPlayers(message.getString("arena.win.place")
                .replace("<name1>", StoragePlacesDB.getPlayers().get(2).getName())
                .replace("<name2>", StoragePlacesDB.getPlayers().get(1).getName())
                .replace("<name3>", StoragePlacesDB.getPlayers().get(0).getName()));

    }
}
