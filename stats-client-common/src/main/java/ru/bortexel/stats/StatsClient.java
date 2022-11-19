package ru.bortexel.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bortexel.stats.dispatcher.NetworkDispatcher;
import ru.bortexel.stats.entities.PlayerAdvancements;
import ru.bortexel.stats.entities.PlayerStats;
import ru.bortexel.stats.external.CustomStatsManager;
import ru.bortexel.stats.requests.UpdatePlayerRequest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class StatsClient {
    private static final Logger logger = LoggerFactory.getLogger("Stats Client");
    private static StatsClient instance;
    private final Config config;
    private final NetworkDispatcher networkDispatcher;
    private final CustomStatsManager customStatsManager;

    public static StatsClient getInstance() {
        return instance;
    }

    public StatsClient(StatsAPI api, Config config) {
        this.config = config;
        this.networkDispatcher = new NetworkDispatcher(config);
        this.customStatsManager = new CustomStatsManager(api.getStatsSuppliers());
    }

    public static void initialize(Path workDir) throws IOException {
        Config config = new Config(workDir.resolve("stats.properties").toFile());
        instance = new StatsClient(StatsAPI.init(), config);
        logger.info("Initialized stats client for server {} with remote {}", config.getServerIdentifier(), config.getRemoteUrl());
    }

    public CompletableFuture<Void> updatePlayer(Player player, PlayerStats stats, PlayerAdvancements advancements) {
        return CompletableFuture.runAsync(() -> {
            PlayerStats playerStats = (PlayerStats) stats.clone();
            PlayerStats customStats = this.getCustomStatsManager().getCustomStats(player).join();
            playerStats.putAll(customStats);

            this.getNetworkDispatcher().dispatchRequest(new UpdatePlayerRequest(
                    this.getConfig().getServerIdentifier(),
                    player.getUniqueId(), player.getName(),
                    playerStats, advancements
            ));
        });
    }

    public Config getConfig() {
        return config;
    }

    public NetworkDispatcher getNetworkDispatcher() {
        return networkDispatcher;
    }

    public CustomStatsManager getCustomStatsManager() {
        return customStatsManager;
    }
}
