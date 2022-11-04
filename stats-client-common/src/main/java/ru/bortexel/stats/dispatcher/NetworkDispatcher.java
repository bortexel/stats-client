package ru.bortexel.stats.dispatcher;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bortexel.stats.requests.Request;
import ru.bortexel.stats.requests.RequestException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class NetworkDispatcher {
    private static final Logger logger = LoggerFactory.getLogger("Stats Network Dispatcher");
    private final DispatcherConfig config;
    private final Gson gson;
    private final HttpClient httpClient;

    public NetworkDispatcher(DispatcherConfig config) {
        this.config = config;
        this.gson = new Gson();
        this.httpClient = HttpClient.newBuilder().build();
    }

    public void dispatchRequest(Request request) {
        try {
            String json = this.getGson().toJson(request);
            this.doHttpRequest(request.getMethod(), json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException | URISyntaxException | InterruptedException e) {
            logger.error("Error dispatching {}", request.getClass().getSimpleName(), e);
        } catch (RequestException exception) {
            exception.setMethod(request.getMethod());
            throw exception;
        }
    }

    private void doHttpRequest(String method, byte[] data) throws IOException, URISyntaxException, InterruptedException {
        URL url = this.getConfig().getRemoteUrl();
        HttpRequest httpRequest = HttpRequest.newBuilder(url.toURI())
                .method(method, HttpRequest.BodyPublishers.ofByteArray(data))
                .header("Content-Type", "application/json")
                .header("Authorization", String.format("Key %s", this.getConfig().getAuthorizationKey()))
                .build();

        HttpResponse<String> response = this.getHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        this.handleResponse(response.statusCode());
    }

    private void handleResponse(int status) {
        if (status < 300) return;
        throw new RequestException(status);
    }

    public DispatcherConfig getConfig() {
        return config;
    }

    public Gson getGson() {
        return gson;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
