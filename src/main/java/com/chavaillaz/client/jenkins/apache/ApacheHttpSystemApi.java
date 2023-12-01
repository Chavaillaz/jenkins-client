package com.chavaillaz.client.jenkins.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.head;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.domain.system.SystemInfo;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.http.NameValuePair;

/**
 * Implementation of {@link SystemApi} for Apache HTTP.
 */
public class ApacheHttpSystemApi extends AbstractApacheHttpClient implements SystemApi {

    /**
     * Creates a new system client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public ApacheHttpSystemApi(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<SystemInfo> getSystemInfo() {
        return sendAsyncBase(requestBuilder(head(), URL_SYSTEM))
                .thenApply(this::systemInfoFromHeader);
    }

    protected SystemInfo systemInfoFromHeader(SimpleHttpResponse response) {
        return new SystemInfo(
                extractHeader(response, "X-Hudson"),
                extractHeader(response, "X-Instance-Identity"),
                extractHeader(response, "X-Jenkins-Session"),
                extractHeader(response, "X-Jenkins"),
                extractHeader(response, "Server"),
                extractHeader(response, "X-SSH-Endpoint")
        );
    }

    protected String extractHeader(SimpleHttpResponse response, String header) {
        return Optional.of(header)
                .map(response::getFirstHeader)
                .map(NameValuePair::getValue)
                .orElse(null);
    }

    @Override
    public CompletableFuture<Void> quietDown() {
        return sendAsync(requestBuilder(post(), URL_QUIET_DOWN_START), Void.class);
    }

    @Override
    public CompletableFuture<Void> cancelQuietDown() {
        return sendAsync(requestBuilder(post(), URL_QUIET_DOWN_CANCELLATION), Void.class);
    }

    @Override
    public CompletableFuture<Void> restart() {
        return sendAsync(requestBuilder(post(), URL_RESTART), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeRestart() {
        return sendAsync(requestBuilder(post(), URL_RESTART_SAFE), Void.class);
    }

    @Override
    public CompletableFuture<Void> exit() {
        return sendAsync(requestBuilder(post(), URL_EXIT), Void.class);
    }

    @Override
    public CompletableFuture<Void> safeExit() {
        return sendAsync(requestBuilder(post(), URL_EXIT_SAFE), Void.class);
    }

    @Override
    public CompletableFuture<Void> reload() {
        return sendAsync(requestBuilder(post(), URL_RELOAD), Void.class);
    }

}
