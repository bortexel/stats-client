package ru.bortexel.stats.names;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class OfflineNameMapper implements NameMapper {
    private final HashMap<UUID, String> nameMap = new HashMap<>();

    public OfflineNameMapper(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            UUID uuid = UUID.nameUUIDFromBytes(String.format("OfflinePlayer:%s", name).getBytes(StandardCharsets.UTF_8));
            this.getNameMap().put(uuid, name);
        }
    }

    @Override
    public @Nullable String getName(UUID uuid) {
        return this.getNameMap().get(uuid);
    }

    public HashMap<UUID, String> getNameMap() {
        return nameMap;
    }
}
