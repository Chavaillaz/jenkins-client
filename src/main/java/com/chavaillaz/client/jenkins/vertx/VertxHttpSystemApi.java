package com.chavaillaz.client.jenkins.vertx;

import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.HEAD;
import static io.vertx.core.http.HttpMethod.POST;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.domain.system.Load;
import com.chavaillaz.client.jenkins.domain.system.SystemInfo;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link SystemApi} for Vert.x HTTP.
 */
public class VertxHttpSystemApi extends AbstractVertxHttpClient implements SystemApi {

    /**
     * Creates a new system client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public VertxHttpSystemApi(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<SystemInfo> getSystemInfo() {
        return handleAsyncBase(requestBuilder(HEAD, URL_SYSTEM).send())
                .thenApply(this::systemInfoFromHeader);
    }

    protected SystemInfo systemInfoFromHeader(HttpResponse<Buffer> response) {
        return new SystemInfo(
                response.getHeader("X-Hudson"),
                response.getHeader("X-Instance-Identity"),
                response.getHeader("X-Jenkins-Session"),
                response.getHeader("X-Jenkins"),
                response.getHeader("Server"),
                response.getHeader("X-SSH-Endpoint")
        );
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return handleAsync(requestBuilder(GET, URL_LOAD).send(), Load.class);
    }

    @Override
    public CompletableFuture<Void> quietDown() {
        return handleAsync(requestBuilder(POST, URL_QUIET_DOWN_START).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> cancelQuietDown() {
        return handleAsync(requestBuilder(POST, URL_QUIET_DOWN_CANCELLATION).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> restart() {
        return handleAsync(requestBuilder(POST, URL_RESTART).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeRestart() {
        return handleAsync(requestBuilder(POST, URL_RESTART_SAFE).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> exit() {
        return handleAsync(requestBuilder(POST, URL_EXIT).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeExit() {
        return handleAsync(requestBuilder(POST, URL_EXIT_SAFE).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> reload() {
        return handleAsync(requestBuilder(POST, URL_RELOAD).send(), Void.class);
    }

}
