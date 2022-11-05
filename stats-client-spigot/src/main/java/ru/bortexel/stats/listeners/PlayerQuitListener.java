package ru.bortexel.stats.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.function.Consumer;

public class PlayerQuitListener implements Listener {
    private final Consumer<Player> updater;

    public PlayerQuitListener(Consumer<Player> updater) {
        this.updater = updater;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.getUpdater().accept(event.getPlayer());
    }

    public Consumer<Player> getUpdater() {
        return updater;
    }
}
