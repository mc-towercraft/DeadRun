package me.towercraft.rolles.minigame.deadrun.listener;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import me.towercraft.rolles.minigame.deadrun.afk.AFKCheck;
import me.towercraft.rolles.minigame.deadrun.enumerate.GameState;
import me.towercraft.rolles.minigame.deadrun.event.GameStartEvent;
import me.towercraft.rolles.minigame.deadrun.handler.ArenaHandler;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageGameStateDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlayersCounterDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageSpectatorsDB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class GameStartListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (StorageGameStateDB.getState() == GameState.WAITING) {
            StoragePlayersCounterDB.incrementValue();
            if (StoragePlayersCounterDB.getValue() == 1) {
                StorageGameStateDB.setState(GameState.STARTING);
                ArenaHandler.timer();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        StoragePlayersCounterDB.decrementValue();
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        StorageGameStateDB.setState(GameState.PLAYING);
        Bukkit.getPluginManager().registerEvents(new ArenaListener(), Deadrun.plugin);
        Deadrun.afkCheck = new AFKCheck();
    }
}
