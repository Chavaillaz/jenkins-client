package com.chavaillaz.client.jenkins.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.domain.Load;

/**
 * Implementation of {@link StatisticsApi} for Apache HTTP.
 */
public class ApacheHttpStatisticsApi extends AbstractApacheHttpClient implements StatisticsApi {

    /**
     * Creates a new statistics client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public ApacheHttpStatisticsApi(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Load> getOverallLoad() {
        return sendAsync(requestBuilder(get(), URL_STATISTICS), Load.class);
    }

}
