package ru.bortexel.stats.parsing;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class PlayerDataParser<T> {
    protected static final Gson GSON = new Gson();
    private final JsonObject root;

    protected PlayerDataParser(JsonObject root) {
        this.root = root;
    }

    protected PlayerDataParser(String data) {
        this(GSON.fromJson(data, JsonElement.class).getAsJsonObject());
    }

    protected PlayerDataParser(Path path) throws IOException {
        this(Files.readString(path));
    }

    public abstract T parse();

    protected JsonObject getRoot() {
        return root;
    }
}
