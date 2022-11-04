package ru.bortexel.stats;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.bortexel.stats.entities.PlayerAdvancements;
import ru.bortexel.stats.entities.PlayerStats;
import ru.bortexel.stats.parsing.AdvancementParser;
import ru.bortexel.stats.parsing.StatsParser;

import java.io.IOException;
import java.nio.file.Path;

public class PlayerDataProvider {
    public static PlayerStats parsePlayerStats(Player player) throws IOException {
        Path path = getMainWorldFolder().resolve("stats").resolve(player.getUniqueId() + ".json");
        StatsParser statsParser = new StatsParser(path);
        return statsParser.parse();
    }

    public static PlayerAdvancements parsePlayerAdvancements(Player player) throws IOException {
        Path path = getMainWorldFolder().resolve("advancements").resolve(player.getUniqueId() + ".json");
        AdvancementParser advancementParser = new AdvancementParser(path);
        return advancementParser.parse();
    }

    private static Path getMainWorldFolder() {
        World world = Bukkit.getServer().getWorlds().get(0);
        return world.getWorldFolder().toPath();
    }
}
