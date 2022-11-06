package ru.bortexel.stats.names;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NullNameMapper implements NameMapper {
    @Override
    public @Nullable String getName(UUID uuid) {
        return null;
    }
}
