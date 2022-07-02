package me.towercraft.rolles.minigame.deadrun.util.storage;

import me.towercraft.rolles.minigame.deadrun.enumerate.GameState;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StorageSpectatorsDB {

    public static void addValue(Player player) {
        Storage.spectators.add(player);
    }

    public static List<Player> getValue() {
        return Storage.spectators;
    }

    public static List<Player> getNotSpectators () {
        return StoragePlayersDB.getValue().keySet().stream().filter(location -> !getValue().contains(location)).collect(Collectors.toList());
    }
}
