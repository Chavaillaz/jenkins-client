package com.chavaillaz.jenkins.client.java;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Optional;

import com.chavaillaz.jenkins.client.AbstractJenkinsClient;
import com.chavaillaz.jenkins.client.JenkinsClient;
import com.chavaillaz.jenkins.client.JobClient;
import com.chavaillaz.jenkins.client.PipelineClient;
import com.chavaillaz.jenkins.client.PluginClient;
import com.chavaillaz.jenkins.client.QueueClient;
import com.chavaillaz.jenkins.client.StatisticsClient;
import com.chavaillaz.jenkins.client.SystemClient;
import com.chavaillaz.jenkins.client.UserClient;

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
        return HttpClient.newBuilder()
                .proxy(Optional.ofNullable(this.proxy)
                        .map(config -> ProxySelector.of(new InetSocketAddress(config.getHost(), config.getPort())))
                        .orElse(ProxySelector.getDefault()))
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    @Override
    public JobClient getJobClient() {
        if (cacheJobClient == null) {
            cacheJobClient = new JavaHttpJobClient(newHttpClient(), jenkinsUrl, authentication);
        }
        return cacheJobClient;
    }

    @Override
    public PipelineClient getPipelineClient() {
        if (cachePipelineClient == null) {
            cachePipelineClient = new JavaHttpPipelineClient(newHttpClient(), jenkinsUrl, authentication);
        }
        return cachePipelineClient;
    }

    @Override
    public PluginClient getPluginClient() {
        if (cachePluginClient == null) {
            cachePluginClient = new JavaHttpPluginClient(newHttpClient(), jenkinsUrl, authentication);
        }
        return cachePluginClient;
    }

    @Override
    public QueueClient getQueueClient() {
        if (cacheQueueClient == null) {
            cacheQueueClient = new JavaHttpQueueClient(newHttpClient(), jenkinsUrl, authentication);
        }
        return cacheQueueClient;
    }

    @Override
    public StatisticsClient getStatisticsClient() {
        if (cacheStatisticsClient == null) {
            cacheStatisticsClient = new JavaHttpStatisticsClient(newHttpClient(), jenkinsUrl, authentication);
        }
        return cacheStatisticsClient;
    }

    @Override
    public SystemClient getSystemClient() {
        if (cacheSystemClient == null) {
            cacheSystemClient = new JavaHttpSystemClient(newHttpClient(), jenkinsUrl, authentication);
        }
        return cacheSystemClient;
    }

    @Override
    public UserClient getUserClient() {
        if (cacheUserClient == null) {
            cacheUserClient = new JavaHttpUserClient(newHttpClient(), jenkinsUrl, authentication);
        }
        return cacheUserClient;
    }

}
