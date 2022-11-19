package ru.bortexel.stats.external;

import ru.bortexel.stats.Player;
import ru.bortexel.stats.entities.PlayerStats;

import java.util.concurrent.CompletableFuture;

public interface StatsSupplier {
    CompletableFuture<PlayerStats> supplyStats(Player player);
}
