package me.towercraft.rolles.minigame.deadrun;

import me.towercraft.rolles.minigame.deadrun.afk.AFKCheck;
import me.towercraft.rolles.minigame.deadrun.command.DeadRunCommand;
import me.towercraft.rolles.minigame.deadrun.listener.GameListener;
import me.towercraft.rolles.minigame.deadrun.spectator.SpectatorFactory;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageSpectatorsDB;
import me.towercraft.rolles.minigame.deadrun.util.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Deadrun extends JavaPlugin {

    public static Deadrun plugin;
    public static AFKCheck afkCheck;
    public static SpectatorFactory spectatorFactory;
    public static StorageSpectatorsDB storageSpectators;
    public static FileConfiguration config;
    public static Configuration message;

    @Override
    public void onEnable() {
        plugin = this;

        Config configEntity = new Config();
        config = configEntity.getConfig();
        message = configEntity.getMessage();

        new DeadRunCommand();
        if (config.getString("arena.spawn") != null) {
            spectatorFactory = new SpectatorFactory(this);
            Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
