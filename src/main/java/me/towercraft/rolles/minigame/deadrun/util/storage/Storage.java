package me.towercraft.rolles.minigame.deadrun.util.storage;

import me.towercraft.rolles.minigame.deadrun.enumerate.GameState;

import me.towercraft.rolles.minigame.deadrun.location.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Storage {
    public static HashMap<Player, Location> playersLocation = new HashMap<>();
    public static List<Player> spectators = new ArrayList<>();
    public static List<Player> places = new ArrayList<>();
    public static Integer playersCounter = 0;
    public static GameState gameState = GameState.WAITING;
}
