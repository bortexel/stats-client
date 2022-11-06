package ru.bortexel.stats.names;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.UUID;

public class PropertiesNameMapper extends Properties implements NameMapper {
    public PropertiesNameMapper(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        this.load(new InputStreamReader(inputStream));
    }

    @Override
    public @Nullable String getName(UUID uuid) {
        return this.getProperty(uuid.toString());
    }
}
