package ru.bortexel.stats.parsing;

import com.google.gson.JsonElement;
import ru.bortexel.stats.entities.PlayerStats;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class StatsParser extends PlayerDataParser<PlayerStats> {
    public StatsParser(Path path) throws IOException {
        super(path);
    }

    @Override
    public PlayerStats parse() {
        PlayerStats result = new PlayerStats();

        for (Map.Entry<String, JsonElement> group : this.getRoot().get("stats").getAsJsonObject().entrySet()) {
            HashMap<String, Object> groupStats = new HashMap<>();

            for (Map.Entry<String, JsonElement> groupStat : group.getValue().getAsJsonObject().entrySet()) {
                groupStats.put(groupStat.getKey(), groupStat.getValue().getAsLong());
            }

            result.put(group.getKey(), groupStats);
        }

        return result;
    }
}
