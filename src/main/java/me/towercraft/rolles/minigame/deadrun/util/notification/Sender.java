package me.towercraft.rolles.minigame.deadrun.util.notification;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static me.towercraft.rolles.minigame.deadrun.Deadrun.config;

public abstract class Sender {

    private static String customiseMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void broadcastMessage(String message) {
        Bukkit.broadcastMessage(customiseMessage(Deadrun.message.getString("prefix") + message));
    }

    public static void sendMessagePlayer(Player player, String message) {
        player.sendMessage(customiseMessage(Deadrun.message.getString("prefix") + message));
    }

    public static void sendTitlePlayer(Player player, String message) {
        player.sendTitle(ChatColor.GREEN + message, "");
    }

    public static void sendNoteStickPlayer(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
    }

    public static void messageForListPlayers(String message) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        players.forEach(player -> sendMessagePlayer(player, message));

    }

    public static void titleForListPlayers(String message) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for (Player player : players) {
            try {
                sendTitlePlayer(player, message);
                sendNoteStickPlayer(player);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void teleportOnSpawn(Player player) {
        List<String> locationConfig = Arrays.asList(config.getString("arena.spawn").split(","));
        Location location = new Location(
                player.getWorld(), Double.parseDouble(locationConfig.get(0)),
                Double.parseDouble(locationConfig.get(1)), Double.parseDouble(locationConfig.get(2)),
                Float.parseFloat(locationConfig.get(3)), Float.parseFloat(locationConfig.get(4)));
        player.teleport(location);
    }
}
