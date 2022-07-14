package me.towercraft.rolles.minigame.deadrun.command;

import me.towercraft.rolles.minigame.deadrun.Deadrun;
import me.towercraft.rolles.minigame.deadrun.util.notification.Sender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DeadRunCommand extends CommandAbstract {

    private final FileConfiguration config;

    public DeadRunCommand() {
        super("dr", Deadrun.plugin);
        this.config = Deadrun.config;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (Objects.equals(args[0], "spawn")) {
            if (sender.isOp()) {
                if (!Objects.equals(config.getString("arena.spawn"), "")) {
                    Player player = (Player) sender;
                    Location playerLocation = player.getLocation();
                    String location = String.format("%1s, %2s, %3s, %4s, %5s", playerLocation.getX(), playerLocation.getY(), playerLocation.getZ(), playerLocation.getPitch(), playerLocation.getYaw());
                    config.set("arena.spawn", location);
                    Deadrun.plugin.saveConfig();
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Спавн арены " + ChatColor.GOLD + location + " установлен");
                } else {
                    Sender.broadcastMessage("&7Спавн уже установлен");
                }
            }
        }
    }
}
