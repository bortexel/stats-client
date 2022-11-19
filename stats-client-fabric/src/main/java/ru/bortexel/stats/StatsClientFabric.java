package ru.bortexel.stats;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class StatsClientFabric implements ModInitializer {
    private static final Logger logger = LoggerFactory.getLogger("Stats Client");
    private static StatsClientFabric instance;

    private MinecraftDedicatedServer server;
    private PlayerDataProvider playerDataProvider;

    public static Optional<StatsClientFabric> getInstance() {
        return Optional.ofNullable(instance);
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            if (!server.isDedicated() || !(server instanceof MinecraftDedicatedServer)) {
                logger.error("StatsClient can be only ran on dedicated server");
                return;
            }

            this.setServer((MinecraftDedicatedServer) server);
            this.initializeClient();
        });
    }

    private void initializeClient() {
        try {
            StatsClient.initialize(FabricLoader.getInstance().getConfigDir());
            this.setPlayerDataProvider(new PlayerDataProvider(this.getServer()));

            instance = this;
        } catch (IOException e) {
            logger.error("Unable to initialize", e);
        }
    }

    public void updatePlayer(ServerPlayerEntity player) {
        try {
            StatsClient.getInstance().updatePlayer(
                    new FabricPlayer(player),
                    this.getPlayerDataProvider().parsePlayerStats(player),
                    this.getPlayerDataProvider().parsePlayerAdvancements(player)
            ).exceptionally(throwable -> {
                logger.error("Error updating stats for {}", player.getName(), throwable);
                return null;
            });
        } catch (Exception e) {
            logger.error("Error collecting stats for {}", player.getName(), e);
        }
    }

    public MinecraftDedicatedServer getServer() {
        return server;
    }

    protected void setServer(MinecraftDedicatedServer server) {
        this.server = server;
    }

    public PlayerDataProvider getPlayerDataProvider() {
        return playerDataProvider;
    }

    protected void setPlayerDataProvider(PlayerDataProvider playerDataProvider) {
        this.playerDataProvider = playerDataProvider;
    }
}
