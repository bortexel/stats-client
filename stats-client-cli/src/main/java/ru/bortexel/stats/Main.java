package ru.bortexel.stats;

import ru.bortexel.stats.entities.PlayerAdvancements;
import ru.bortexel.stats.entities.PlayerStats;
import ru.bortexel.stats.names.NameMapper;
import ru.bortexel.stats.names.NullNameMapper;
import ru.bortexel.stats.names.PropertiesNameMapper;
import ru.bortexel.stats.parsing.AdvancementParser;
import ru.bortexel.stats.parsing.StatsParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("World path must be provided as argument");
            return;
        }

        Path path = Path.of(args[0]);
        if (!path.toFile().exists()) {
            System.err.println("Specified path does not exist");
            return;
        }

        final NameMapper nameMapper;
        if (args.length == 2) nameMapper = new PropertiesNameMapper(Path.of(args[1]).toFile());
        else nameMapper = new NullNameMapper();

        StatsClient.initialize(path);

        HashMap<UUID, PlayerStats> allPlayerStats = new HashMap<>();
        HashMap<UUID, PlayerAdvancements> allPlayerAdvancements = new HashMap<>();

        DataResolver dataResolver = new DataResolver(path);

        for (File statsFile : dataResolver.findStatsFiles()) {
            PlayerStats playerStats = acceptStatsFile(statsFile);
            String uuid = statsFile.getName().split("\\.")[0];
            allPlayerStats.put(UUID.fromString(uuid), playerStats);
        }

        for (File advancementsFile : dataResolver.findAdvancementFiles()) {
            PlayerAdvancements playerAdvancements = acceptAdvancementsFile(advancementsFile);
            String uuid = advancementsFile.getName().split("\\.")[0];
            allPlayerAdvancements.put(UUID.fromString(uuid), playerAdvancements);
        }

        allPlayerStats.forEach((uuid, playerStats) -> {
            if (!allPlayerAdvancements.containsKey(uuid)) return;
            PlayerAdvancements playerAdvancements = allPlayerAdvancements.get(uuid);
            SimplePlayer player = new SimplePlayer(nameMapper.getName(uuid), uuid);
            StatsClient.getInstance().updatePlayer(player, playerStats, playerAdvancements);
        });
    }

    public static PlayerAdvancements acceptAdvancementsFile(File file) {
        try {
            return new AdvancementParser(file.toPath()).parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PlayerStats acceptStatsFile(File file) {
        try {
            return new StatsParser(file.toPath()).parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}