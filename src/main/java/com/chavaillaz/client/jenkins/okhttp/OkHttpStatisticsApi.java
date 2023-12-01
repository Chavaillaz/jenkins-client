package com.chavaillaz.client.jenkins.okhttp;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.domain.system.Load;
import okhttp3.OkHttpClient;

/**
 * Implementation of {@link StatisticsApi} for OkHttp.
 */
public class OkHttpStatisticsApi extends AbstractOkHttpClient implements StatisticsApi {

    /**
     * Creates a new statistics client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public OkHttpStatisticsApi(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return sendAsync(requestBuilder(URL_STATISTICS).get(), Load.class);
    }

}
