package com.chavaillaz.client.jenkins.java;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.StatisticsClient;
import com.chavaillaz.client.jenkins.domain.Load;

/**
 * Implementation of {@link StatisticsClient} for Java HTTP.
 */
public class JavaHttpStatisticsClient extends AbstractJavaHttpClient implements StatisticsClient {

    /**
     * Creates a new statistics client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpStatisticsClient(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return sendAsync(requestBuilder(URL_STATISTICS).GET(), Load.class);
    }

}