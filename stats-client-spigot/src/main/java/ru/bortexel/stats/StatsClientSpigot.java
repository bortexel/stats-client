package ru.bortexel.stats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bortexel.stats.listeners.PlayerJoinListener;

import java.io.IOException;
import java.util.logging.Level;

public final class StatsClientSpigot extends JavaPlugin {
    private static final Logger logger = LoggerFactory.getLogger("Stats Client");

    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        try {
            StatsClient.initialize(this.getDataFolder().toPath());
            pluginManager.registerEvents(new PlayerJoinListener(this::updatePlayer), this);
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "Unable to initialize", e);
            pluginManager.disablePlugin(this);
        }
    }

    public void updatePlayer(Player player) {
        try {
            StatsClient.getInstance().updatePlayer(
                    new SpigotPlayer(player),
                    PlayerDataProvider.parsePlayerStats(player),
                    PlayerDataProvider.parsePlayerAdvancements(player)
            );
        } catch (IOException e) {
            logger.error("Error updating stats for {}", player.getName(), e);
        }
    }
}
