package ru.bortexel.stats;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class FabricPlayer implements Player {
    private final ServerPlayerEntity handle;

    public FabricPlayer(ServerPlayerEntity handle) {
        this.handle = handle;
    }

    public ServerPlayerEntity getHandle() {
        return handle;
    }

    @Override
    public String getName() {
        return this.getHandle().getEntityName();
    }

    @Override
    public UUID getUniqueId() {
        return this.getHandle().getUuid();
    }
}
