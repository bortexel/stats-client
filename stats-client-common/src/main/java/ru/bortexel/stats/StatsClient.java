package ru.bortexel.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bortexel.stats.dispatcher.NetworkDispatcher;
import ru.bortexel.stats.entities.PlayerAdvancements;
import ru.bortexel.stats.entities.PlayerStats;
import ru.bortexel.stats.requests.UpdatePlayerRequest;

import java.io.IOException;
import java.nio.file.Path;

public class StatsClient {
    private static final Logger logger = LoggerFactory.getLogger("Stats Client");
    private static StatsClient instance;
    private final Config config;
    private final NetworkDispatcher networkDispatcher;

    public static StatsClient getInstance() {
        return instance;
    }

    public StatsClient(Config config) {
        this.config = config;
        this.networkDispatcher = new NetworkDispatcher(config);
    }

    public static void initialize(Path workDir) throws IOException {
        Config config = new Config(workDir.resolve("stats.properties").toFile());
        instance = new StatsClient(config);
        logger.info("Initialized stats client for server {} with remote {}", config.getServerIdentifier(), config.getRemoteUrl());
    }

    public void updatePlayer(Player player, PlayerStats stats, PlayerAdvancements advancements) {
        this.getNetworkDispatcher().dispatchRequest(new UpdatePlayerRequest(
                this.getConfig().getServerIdentifier(),
                player.getUniqueId(), player.getName(),
                stats, advancements
        ));
    }

    public Config getConfig() {
        return config;
    }

    public NetworkDispatcher getNetworkDispatcher() {
        return networkDispatcher;
    }
}
