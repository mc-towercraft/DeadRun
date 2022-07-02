package me.towercraft.rolles.minigame.deadrun.handler;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import me.towercraft.rolles.minigame.deadrun.blockbelow.BlockBelow;
import me.towercraft.rolles.minigame.deadrun.event.GameStartEvent;
import me.towercraft.rolles.minigame.deadrun.location.Location;
import me.towercraft.rolles.minigame.deadrun.util.storage.StoragePlayersDB;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageSpectatorsDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArenaHandler {

    public static void removeBlocks(Block block) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Deadrun.plugin, () -> {
            if (block.getType() != Material.AIR) {
                block.getRelative(BlockFace.DOWN).setType(Material.AIR);
                block.setType(Material.AIR);
            }
        }, 8);
    }

    public static void afkCheck() {
       Bukkit.getScheduler().scheduleAsyncRepeatingTask(Deadrun.plugin, () -> {
           HashMap<Player, Location> playerLocation = new HashMap<>();

           StorageSpectatorsDB.getNotSpectators().forEach(player -> playerLocation.put(player, new Location(
                   (int) player.getLocation().getX(), (int) player.getLocation().getZ())));

           List<Player> afkPlayers = StoragePlayersDB.getValue().entrySet()
                   .stream().filter(player -> playerLocation.get(player.getKey()).equals(player.getValue()))
                   .map(Map.Entry::getKey).collect(Collectors.toList());

           System.out.println(afkPlayers);
           if (afkPlayers.size() >= 1) {
               afkPlayers.forEach(player -> new BlockBelow(player).getBlocks().forEach(ArenaHandler::removeBlocks));
           }
            StoragePlayersDB.setMap(playerLocation);
       }, 0, 8);
    }

    public static void timer() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Deadrun.plugin, () -> {
            for (int i = 3; i >= 0; i--) {
                Bukkit.broadcastMessage("Осталось: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i == 0) {
                    Bukkit.getPluginManager().callEvent(new GameStartEvent());
                }
            }
        }, 0);

    }
}
