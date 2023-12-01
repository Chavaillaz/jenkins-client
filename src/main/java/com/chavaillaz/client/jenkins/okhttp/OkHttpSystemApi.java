package com.chavaillaz.client.jenkins.okhttp;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.domain.system.Load;
import com.chavaillaz.client.jenkins.domain.system.SystemInfo;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Implementation of {@link SystemApi} for OkHttp.
 */
public class OkHttpSystemApi extends AbstractOkHttpClient implements SystemApi {

    /**
     * Creates a new system client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public OkHttpSystemApi(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<SystemInfo> getSystemInfo() {
        return sendAsyncBase(requestBuilder(URL_SYSTEM).head())
                .thenApply(this::systemInfoFromHeader);
    }

    protected SystemInfo systemInfoFromHeader(Response response) {
        return new SystemInfo(
                response.header("X-Hudson"),
                response.header("X-Instance-Identity"),
                response.header("X-Jenkins-Session"),
                response.header("X-Jenkins"),
                response.header("Server"),
                response.header("X-SSH-Endpoint")
        );
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return sendAsync(requestBuilder(URL_LOAD).get(), Load.class);
    }

    @Override
    public CompletableFuture<Void> quietDown() {
        return sendAsync(requestBuilder(URL_QUIET_DOWN_START).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> cancelQuietDown() {
        return sendAsync(requestBuilder(URL_QUIET_DOWN_CANCELLATION).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> restart() {
        return sendAsync(requestBuilder(URL_RESTART).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeRestart() {
        return sendAsync(requestBuilder(URL_RESTART_SAFE).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> exit() {
        return sendAsync(requestBuilder(URL_EXIT).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeExit() {
        return sendAsync(requestBuilder(URL_EXIT_SAFE).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> reload() {
        return sendAsync(requestBuilder(URL_RELOAD).post(EMPTY_BODY), Void.class);
    }

}
