package ru.bortexel.stats;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.bortexel.stats.entities.PlayerAdvancements;
import ru.bortexel.stats.entities.PlayerStats;
import ru.bortexel.stats.parsing.AdvancementParser;
import ru.bortexel.stats.parsing.StatsParser;

import java.io.IOException;
import java.nio.file.Path;

public class PlayerDataProvider {
    private final MinecraftDedicatedServer server;

    public PlayerDataProvider(MinecraftDedicatedServer server) {
        this.server = server;
    }

    public PlayerStats parsePlayerStats(ServerPlayerEntity player) throws IOException {
        Path path = this.getMainWorldFolder().resolve("stats").resolve(player.getUuidAsString() + ".json");
        StatsParser statsParser = new StatsParser(path);
        return statsParser.parse();
    }

    public PlayerAdvancements parsePlayerAdvancements(ServerPlayerEntity player) throws IOException {
        Path path = this.getMainWorldFolder().resolve("advancements").resolve(player.getUuidAsString() + ".json");
        AdvancementParser advancementParser = new AdvancementParser(path);
        return advancementParser.parse();
    }

    private Path getMainWorldFolder() {
        Path gameDir = FabricLoader.getInstance().getGameDir();
        return gameDir.resolve(this.getServer().getProperties().levelName);
    }

    public MinecraftDedicatedServer getServer() {
        return server;
    }
}
