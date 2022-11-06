package ru.bortexel.stats.names;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface NameMapper {
    @Nullable String getName(UUID uuid);
}
