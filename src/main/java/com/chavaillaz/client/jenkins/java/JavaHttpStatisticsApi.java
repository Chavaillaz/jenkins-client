package com.chavaillaz.client.jenkins.java;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.domain.system.Load;

/**
 * Implementation of {@link StatisticsApi} for Java HTTP.
 */
public class JavaHttpStatisticsApi extends AbstractJavaHttpClient implements StatisticsApi {

    /**
     * Creates a new statistics client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpStatisticsApi(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return sendAsync(requestBuilder(URL_STATISTICS).GET(), Load.class);
    }

}
