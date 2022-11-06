package ru.bortexel.stats;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class DataResolver {
    private static final String DATA_FILE_NAME_REGEX = "[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}.json";

    private final Path worldPath;

    public DataResolver(Path worldPath) {
        this.worldPath = worldPath;
    }

    public List<File> findStatsFiles() {
        File statsDirectory = this.getWorldPath().resolve("stats").toFile();
        File[] result = statsDirectory.listFiles((file) -> file.getName().matches(DATA_FILE_NAME_REGEX));
        return result == null ? List.of() : List.of(result);
    }

    public List<File> findAdvancementFiles() {
        File advancementsDirectory = this.getWorldPath().resolve("advancements").toFile();
        File[] result = advancementsDirectory.listFiles((file) -> file.getName().matches(DATA_FILE_NAME_REGEX));
        return result == null ? List.of() : List.of(result);
    }

    public Path getWorldPath() {
        return worldPath;
    }
}
