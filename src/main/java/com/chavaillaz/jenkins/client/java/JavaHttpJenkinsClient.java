package com.chavaillaz.jenkins.client.java;

import java.net.http.HttpClient;

import com.chavaillaz.client.java.JavaHttpUtils;
import com.chavaillaz.jenkins.client.AbstractJenkinsClient;
import com.chavaillaz.jenkins.client.JenkinsClient;
import com.chavaillaz.jenkins.client.JobClient;
import com.chavaillaz.jenkins.client.PipelineClient;
import com.chavaillaz.jenkins.client.PluginClient;
import com.chavaillaz.jenkins.client.QueueClient;
import com.chavaillaz.jenkins.client.StatisticsClient;
import com.chavaillaz.jenkins.client.SystemClient;
import com.chavaillaz.jenkins.client.UserClient;

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

    /**
     * Creates a default {@link JenkinsClient} with Java HTTP client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    public static JavaHttpJenkinsClient jenkinsJavaClient(String jenkinsUrl) {
        return new JavaHttpJenkinsClient(jenkinsUrl);
    }

    @Override
    public HttpClient newHttpClient() {
        return JavaHttpUtils.newHttpClient(proxy);
    }

    @Override
    public JobClient getJobClient() {
        return cacheJobClient.get(() -> new JavaHttpJobClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PipelineClient getPipelineClient() {
        return cachePipelineClient.get(() -> new JavaHttpPipelineClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PluginClient getPluginClient() {
        return cachePluginClient.get(() -> new JavaHttpPluginClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public QueueClient getQueueClient() {
        return cacheQueueClient.get(() -> new JavaHttpQueueClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public StatisticsClient getStatisticsClient() {
        return cacheStatisticsClient.get(() -> new JavaHttpStatisticsClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public SystemClient getSystemClient() {
        return cacheSystemClient.get(() -> new JavaHttpSystemClient(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public UserClient getUserClient() {
        return cacheUserClient.get(() -> new JavaHttpUserClient(newHttpClient(), baseUrl, authentication));
    }

}
