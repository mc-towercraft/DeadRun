package me.towercraft.rolles.minigame.deadrun.afk;

import me.towercraft.rolles.minigame.deadrun.handler.ArenaHandler;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlayersDB;
import org.bukkit.Bukkit;

public final class AFKCheck {

    public AFKCheck() {
        Bukkit.getOnlinePlayers().forEach(StoragePlayersDB::addValue);
        ArenaHandler.afkCheck();
    }
}
