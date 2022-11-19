package ru.bortexel.stats.external;

import ru.bortexel.stats.Player;
import ru.bortexel.stats.entities.PlayerStats;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CustomStatsManager {
    private final List<StatsSupplier> statsSuppliers;

    public CustomStatsManager(List<StatsSupplier> statsSuppliers) {
        this.statsSuppliers = statsSuppliers;
    }

    public CompletableFuture<PlayerStats> getCustomStats(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            PlayerStats finalStats = new PlayerStats();

            for (StatsSupplier statsSupplier : this.getStatsSuppliers()) {
                PlayerStats playerStats = statsSupplier.supplyStats(player).join();
                finalStats.putAll(playerStats);
            }

            return finalStats;
        });
    }

    private List<StatsSupplier> getStatsSuppliers() {
        return statsSuppliers;
    }
}
