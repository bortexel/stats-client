package ru.bortexel.stats;

import java.util.UUID;

public class SimplePlayer implements Player {
    private final String name;
    private final UUID uniqueId;

    public SimplePlayer(String name, UUID uniqueId) {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }
}
