package com.chavaillaz.client.jenkins.java;

import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.net.http.HttpResponse.BodyHandlers.discarding;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.domain.system.SystemInfo;

/**
 * Implementation of {@link SystemApi} for Java HTTP.
 */
public class JavaHttpSystemApi extends AbstractJavaHttpClient implements SystemApi {

    /**
     * Creates a new system client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpSystemApi(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<SystemInfo> getSystemInfo() {
        return client.sendAsync(requestBuilder(URL_SYSTEM).method("HEAD", noBody()).build(), discarding())
                .thenApply(this::checkResponse)
                .thenApply(this::systemInfoFromHeader);
    }

    protected SystemInfo systemInfoFromHeader(HttpResponse<Void> response) {
        return new SystemInfo(
                response.headers().firstValue("X-Hudson").orElse(null),
                response.headers().firstValue("X-Instance-Identity").orElse(null),
                response.headers().firstValue("X-Jenkins-Session").orElse(null),
                response.headers().firstValue("X-Jenkins").orElse(null),
                response.headers().firstValue("Server").orElse(null),
                response.headers().firstValue("X-SSH-Endpoint").orElse(null)
        );
    }

    @Override
    public CompletableFuture<Void> quietDown() {
        return sendAsync(requestBuilder(URL_QUIET_DOWN_START).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> cancelQuietDown() {
        return sendAsync(requestBuilder(URL_QUIET_DOWN_CANCELLATION).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> restart() {
        return sendAsync(requestBuilder(URL_RESTART).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeRestart() {
        return sendAsync(requestBuilder(URL_RESTART_SAFE).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> exit() {
        return sendAsync(requestBuilder(URL_EXIT).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeExit() {
        return sendAsync(requestBuilder(URL_EXIT_SAFE).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> reload() {
        return sendAsync(requestBuilder(URL_RELOAD).POST(noBody()), Void.class);
    }

}
