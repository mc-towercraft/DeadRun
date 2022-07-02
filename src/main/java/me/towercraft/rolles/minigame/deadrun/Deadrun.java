package me.towercraft.rolles.minigame.deadrun;

import me.towercraft.rolles.minigame.deadrun.afk.AFKCheck;
import me.towercraft.rolles.minigame.deadrun.listener.GameStartListener;
import me.towercraft.rolles.minigame.deadrun.spectator.SpectatorFactory;
import me.towercraft.rolles.minigame.deadrun.util.storage.StorageSpectatorsDB;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Deadrun extends JavaPlugin {

    public static Deadrun plugin;
    public static AFKCheck afkCheck;
    public static SpectatorFactory spectatorFactory;
    public static StorageSpectatorsDB storageSpectators;

    @Override
    public void onEnable() {
        plugin = this;
        spectatorFactory = new SpectatorFactory(this);
        Bukkit.getPluginManager().registerEvents(new GameStartListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
