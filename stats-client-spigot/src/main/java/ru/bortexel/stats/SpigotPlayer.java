package ru.bortexel.stats;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class SpigotPlayer implements Player {
    private final org.bukkit.OfflinePlayer handle;

    public SpigotPlayer(OfflinePlayer handle) {
        this.handle = handle;
    }

    public OfflinePlayer getHandle() {
        return handle;
    }

    @Override
    public String getName() {
        return this.getHandle().getName();
    }

    @Override
    public UUID getUniqueId() {
        return this.getHandle().getUniqueId();
    }
}
