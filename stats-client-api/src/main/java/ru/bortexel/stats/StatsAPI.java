package ru.bortexel.stats;

import ru.bortexel.stats.external.StatsSupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class StatsAPI {
    private static StatsAPI instance;
    private static final List<Consumer<StatsAPI>> startupListeners = new ArrayList<>();

    private final List<StatsSupplier> statsSuppliers = new ArrayList<>();

    public static void onInitialize(Consumer<StatsAPI> listener) {
        startupListeners.add(listener);
    }

    public static StatsAPI getInstance() {
        if (instance == null) throw new IllegalStateException("StatsAPI is not ready yet");
        return instance;
    }

    protected static StatsAPI init() {
        if (instance != null) throw new IllegalStateException("StatsAPI is already initialized");
        instance = new StatsAPI();
        startupListeners.forEach(consumer -> consumer.accept(instance));
        return instance;
    }

    public void registerStatsSupplier(StatsSupplier statsSupplier) {
        this.getStatsSuppliers().add(statsSupplier);
    }

    protected List<StatsSupplier> getStatsSuppliers() {
        return statsSuppliers;
    }
}
