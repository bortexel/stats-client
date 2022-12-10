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

    /**
     * Supplies StatsAPI instance after it has been initialized. If StatsAPI is already initialized, supplies
     * its instance immediately.
     * @param listener Consumer of StatsAPI that will be invoked when the event is fired.
     */
    public static void afterInitialized(Consumer<StatsAPI> listener) {
        if (instance != null) {
            listener.accept(instance);
            return;
        }

        onInitialize(listener);
    }

    /**
     * Supplies StatsAPI instance when it is being initialized. If StatsAPI is already initialized, nothing happens.
     * @param listener Consumer of StatsAPI that will be invoked when the event is fired.
     */
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
