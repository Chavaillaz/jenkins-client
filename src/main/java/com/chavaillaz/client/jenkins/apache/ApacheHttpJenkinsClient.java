package com.chavaillaz.client.jenkins.apache;

import com.chavaillaz.client.common.apache.ApacheHttpUtils;
import com.chavaillaz.client.jenkins.AbstractJenkinsClient;
import com.chavaillaz.client.jenkins.JenkinsClient;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.api.UserApi;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

/**
 * Implementation of {@link JenkinsClient} for Apache HTTP.
 */
public class ApacheHttpJenkinsClient extends AbstractJenkinsClient<CloseableHttpAsyncClient> {

    /**
     * Creates a new {@link JenkinsClient} with Apache HTTP client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    public ApacheHttpJenkinsClient(String jenkinsUrl) {
        super(jenkinsUrl);
    }

    @Override
    public CloseableHttpAsyncClient newHttpClient() {
        return ApacheHttpUtils.newHttpClientBuilder(proxy).build();
    }

    @Override
    public JobApi getJobApi() {
        return jobApi.get(() -> new ApacheHttpJobApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PipelineApi getPipelineApi() {
        return pipelineApi.get(() -> new ApacheHttpPipelineApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PluginApi getPluginApi() {
        return pluginApi.get(() -> new ApacheHttpPluginApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public QueueApi getQueueApi() {
        return queueApi.get(() -> new ApacheHttpQueueApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public StatisticsApi getStatisticsApi() {
        return statisticsApi.get(() -> new ApacheHttpStatisticsApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public SystemApi getSystemApi() {
        return systemApi.get(() -> new ApacheHttpSystemApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new ApacheHttpUserApi(newHttpClient(), baseUrl, authentication));
    }

}
