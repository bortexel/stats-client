package ru.bortexel.stats.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.function.Consumer;

public class PlayerJoinListener implements Listener {
    private final Consumer<Player> updater;

    public PlayerJoinListener(Consumer<Player> updater) {
        this.updater = updater;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.getUpdater().accept(event.getPlayer());
    }

    public Consumer<Player> getUpdater() {
        return updater;
    }
}
