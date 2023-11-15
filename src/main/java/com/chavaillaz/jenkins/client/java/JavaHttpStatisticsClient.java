package com.chavaillaz.jenkins.client.java;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jenkins.client.Authentication;
import com.chavaillaz.jenkins.client.StatisticsClient;
import com.chavaillaz.jenkins.domain.Load;

public class JavaHttpStatisticsClient extends AbstractJavaHttpClient implements StatisticsClient {

    /**
     * Creates a new statistics client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpStatisticsClient(HttpClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return sendAsync(requestBuilder(URL_STATISTICS).GET(), Load.class);
    }

}
