package ru.bortexel.stats.parsing;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.bortexel.stats.entities.AdvancementInput;
import ru.bortexel.stats.entities.PlayerAdvancements;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class AdvancementParser extends PlayerDataParser<PlayerAdvancements> {
    public AdvancementParser(Path path) throws IOException {
        super(path);
    }

    @Override
    public PlayerAdvancements parse() {
        PlayerAdvancements result = new PlayerAdvancements();

        for (Map.Entry<String, JsonElement> entry : this.getRoot().getAsJsonObject().entrySet()) {
            if (entry.getValue().isJsonPrimitive()) continue; // Skip DataVersion
            JsonObject advancement = entry.getValue().getAsJsonObject();
            if (entry.getKey().contains("recipes/") || entry.getKey().contains("/root")) continue;

            AdvancementInput advancementInput = new AdvancementInput(
                    entry.getKey(),
                    advancement.get("done").getAsBoolean()
            );

            result.add(advancementInput);
        }

        return result;
    }
}
