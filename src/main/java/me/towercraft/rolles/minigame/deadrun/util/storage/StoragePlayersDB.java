package me.towercraft.rolles.minigame.deadrun.util.storage;

import me.towercraft.rolles.minigame.deadrun.location.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class StoragePlayersDB {

    public static void addValue(Player player) {
        Location location = new Location(
                (int) player.getLocation().getX(), (int) player.getLocation().getZ());
        Storage.playersLocation.put(player, location);
    }

    public static void setMap(HashMap<Player, Location> value) {
        Storage.playersLocation = value;
    }

    public static HashMap<Player, Location> getValue() {
        return Storage.playersLocation;
    }
}
