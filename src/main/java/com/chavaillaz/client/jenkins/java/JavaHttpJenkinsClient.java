package com.chavaillaz.client.jenkins.java;

import java.net.http.HttpClient;

import com.chavaillaz.client.common.java.JavaHttpUtils;
import com.chavaillaz.client.jenkins.AbstractJenkinsClient;
import com.chavaillaz.client.jenkins.JenkinsClient;
import com.chavaillaz.client.jenkins.api.JobClient;
import com.chavaillaz.client.jenkins.api.PipelineClient;
import com.chavaillaz.client.jenkins.api.PluginClient;
import com.chavaillaz.client.jenkins.api.QueueClient;
import com.chavaillaz.client.jenkins.api.StatisticsClient;
import com.chavaillaz.client.jenkins.api.SystemClient;
import com.chavaillaz.client.jenkins.api.UserClient;

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
        return JavaHttpUtils.newHttpClient(proxy);
    }

    @Override
    public JobClient getJobClient() {
        return jobClient.get(() -> new JavaHttpJobClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PipelineClient getPipelineClient() {
        return pipelineClient.get(() -> new JavaHttpPipelineClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PluginClient getPluginClient() {
        return pluginClient.get(() -> new JavaHttpPluginClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public QueueClient getQueueClient() {
        return queueClient.get(() -> new JavaHttpQueueClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public StatisticsClient getStatisticsClient() {
        return statisticsClient.get(() -> new JavaHttpStatisticsClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public SystemClient getSystemClient() {
        return systemClient.get(() -> new JavaHttpSystemClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return userClient.get(() -> new JavaHttpUserClient(newHttpClient(), baseUrl, authentication));
    }

}
