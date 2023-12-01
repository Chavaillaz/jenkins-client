package com.chavaillaz.client.jenkins.vertx;

import static io.vertx.core.http.HttpMethod.GET;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.domain.Load;

import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link StatisticsApi} for Vert.x HTTP.
 */
public class VertxHttpStatisticsApi extends AbstractVertxHttpClient implements StatisticsApi {

    /**
     * Creates a new statistics client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public VertxHttpStatisticsApi(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return handleAsync(requestBuilder(GET, URL_STATISTICS).send(), Load.class);
    }

}
