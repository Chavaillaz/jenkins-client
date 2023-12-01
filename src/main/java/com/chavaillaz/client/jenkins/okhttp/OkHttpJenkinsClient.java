package com.chavaillaz.client.jenkins.okhttp;

import com.chavaillaz.client.common.okhttp.OkHttpUtils;
import com.chavaillaz.client.jenkins.AbstractJenkinsClient;
import com.chavaillaz.client.jenkins.JenkinsClient;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.api.UserApi;

import okhttp3.OkHttpClient;

/**
 * Implementation of {@link JenkinsClient} for OkHttp.
 */
public class OkHttpJenkinsClient extends AbstractJenkinsClient<OkHttpClient> {

    /**
     * Creates a new {@link JenkinsClient} with OkHttp client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    public OkHttpJenkinsClient(String jenkinsUrl) {
        super(jenkinsUrl);
    }

    @Override
    public OkHttpClient newHttpClient() {
        return OkHttpUtils.newHttpClient(proxy);
    }

    @Override
    public JobApi getJobApi() {
        return jobApi.get(() -> new OkHttpJobApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PipelineApi getPipelineApi() {
        return pipelineApi.get(() -> new OkHttpPipelineApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PluginApi getPluginApi() {
        return pluginApi.get(() -> new OkHttpPluginApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public QueueApi getQueueApi() {
        return queueApi.get(() -> new OkHttpQueueApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public StatisticsApi getStatisticsApi() {
        return statisticsApi.get(() -> new OkHttpStatisticsApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public SystemApi getSystemApi() {
        return systemApi.get(() -> new OkHttpSystemApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new OkHttpUserApi(newHttpClient(), baseUrl, authentication));
    }

}
