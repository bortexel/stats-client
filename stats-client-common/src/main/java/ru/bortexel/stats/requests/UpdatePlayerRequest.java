package ru.bortexel.stats.requests;

import ru.bortexel.stats.entities.AdvancementInput;
import ru.bortexel.stats.entities.PlayerStats;
import ru.bortexel.stats.entities.ServerIdentifier;

import java.util.List;
import java.util.UUID;

public record UpdatePlayerRequest(
        ServerIdentifier server,
        UUID uuid,
        String name,
        PlayerStats stats,
        List<AdvancementInput> advancements
) implements Request {
    @Override
    public String getMethod() {
        return "PATCH";
    }
}
