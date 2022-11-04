package ru.bortexel.stats;

import ru.bortexel.stats.dispatcher.DispatcherConfig;
import ru.bortexel.stats.entities.ServerIdentifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Config implements DispatcherConfig {
    private final Properties properties;

    public Config(File file) throws IOException {
        this.properties = new Properties();

        if (file.exists()) {
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            this.properties.load(reader);
        } else makeConfigFile(file);
    }

    private static void makeConfigFile(File file) throws IOException {
        if (file.toPath().resolve("..").toFile().mkdirs()) {
            // noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
    }

    public URL getRemoteUrl() {
        try {
            String urlString = this.getProperties().getProperty("remote.url", "https://stats.bortexel.net/");
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAuthorizationKey() {
        return this.getProperties().getProperty("auth.key");
    }

    public ServerIdentifier getServerIdentifier() {
        return new ServerIdentifier(
                this.getProperties().getProperty("server.name", "main"),
                Integer.parseInt(this.getProperties().getProperty("server.season", "1"))
        );
    }

    public Properties getProperties() {
        return properties;
    }
}
