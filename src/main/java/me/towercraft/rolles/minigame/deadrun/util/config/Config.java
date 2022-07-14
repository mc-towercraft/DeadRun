package me.towercraft.rolles.minigame.deadrun.util.config;

import com.google.common.io.ByteStreams;
import me.towercraft.rolles.minigame.deadrun.Deadrun;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Config {

    private Configuration message;
    private final FileConfiguration config;

    public Config() {
        Deadrun plugin = Deadrun.plugin;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveDefaultConfig();
        }

        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (InputStream resourceAsStream = plugin.getResource("messages.yml")) {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                        ByteStreams.copy(resourceAsStream, fileOutputStream);
                        message = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex2) {
                throw new RuntimeException("Unable to create config file", ex2);
            }
        } else {
            message = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
        }
        config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public Configuration getMessage() {
        return message;
    }
}
