package me.towercraft.rolles.minigame.deadrun.util.storage;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class StoragePlacesDB {

    public static void addPlayer(Player player) {
        Storage.places.add(player);
    }

    public static List<Player> getPlayers() {
       return Storage.places;
    }
}
