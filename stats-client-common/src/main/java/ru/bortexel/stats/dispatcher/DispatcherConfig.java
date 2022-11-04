package ru.bortexel.stats.dispatcher;

import java.net.URL;

public interface DispatcherConfig {
    URL getRemoteUrl();
    String getAuthorizationKey();
}
