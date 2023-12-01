package com.chavaillaz.client.jenkins.java;

import java.net.http.HttpClient;

import com.chavaillaz.client.common.java.JavaHttpUtils;
import com.chavaillaz.client.jenkins.AbstractJenkinsClient;
import com.chavaillaz.client.jenkins.JenkinsClient;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.api.UserApi;

/**
 * Implementation of {@link JenkinsClient} for Java HTTP.
 */
public class JavaHttpJenkinsClient extends AbstractJenkinsClient<HttpClient> {

    /**
     * Creates a new {@link JenkinsClient} with Java HTTP client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    public JavaHttpJenkinsClient(String jenkinsUrl) {
        super(jenkinsUrl);
    }

    @Override
    public HttpClient newHttpClient() {
        return JavaHttpUtils.newHttpClientBuilder(proxy).build();
    }

    @Override
    public JobApi getJobApi() {
        return jobApi.get(() -> new JavaHttpJobApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PipelineApi getPipelineApi() {
        return pipelineApi.get(() -> new JavaHttpPipelineApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PluginApi getPluginApi() {
        return pluginApi.get(() -> new JavaHttpPluginApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public QueueApi getQueueApi() {
        return queueApi.get(() -> new JavaHttpQueueApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public StatisticsApi getStatisticsApi() {
        return statisticsApi.get(() -> new JavaHttpStatisticsApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public SystemApi getSystemApi() {
        return systemApi.get(() -> new JavaHttpSystemApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new JavaHttpUserApi(newHttpClient(), baseUrl, authentication));
    }

}
