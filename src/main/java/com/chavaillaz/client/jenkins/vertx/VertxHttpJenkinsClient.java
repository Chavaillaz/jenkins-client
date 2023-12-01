package com.chavaillaz.client.jenkins.vertx;

import static com.chavaillaz.client.common.vertx.VertxUtils.defaultWebClientOptions;

import com.chavaillaz.client.jenkins.AbstractJenkinsClient;
import com.chavaillaz.client.jenkins.JenkinsClient;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.api.UserApi;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link JenkinsClient} for Vert.x HTTP.
 */
public class VertxHttpJenkinsClient extends AbstractJenkinsClient<WebClient> {

    protected final Vertx vertx;

    /**
     * Creates a new {@link JenkinsClient} with Vert.x client.
     *
     * @param vertx      The Vert.x instance
     * @param jenkinsUrl The Jenkins URL
     */
    public VertxHttpJenkinsClient(Vertx vertx, String jenkinsUrl) {
        super(jenkinsUrl);
        this.vertx = vertx;
    }

    @Override
    public WebClient newHttpClient() {
        return WebClient.create(vertx, defaultWebClientOptions(proxy));
    }

    @Override
    public JobApi getJobApi() {
        return jobApi.get(() -> new VertxHttpJobApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PipelineApi getPipelineApi() {
        return pipelineApi.get(() -> new VertxHttpPipelineApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public PluginApi getPluginApi() {
        return pluginApi.get(() -> new VertxHttpPluginApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public QueueApi getQueueApi() {
        return queueApi.get(() -> new VertxHttpQueueApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public SystemApi getSystemApi() {
        return systemApi.get(() -> new VertxHttpSystemApi(newHttpClient(), baseUrl, authentication));
    }

    @Override
    public UserApi getUserApi() {
        return userApi.get(() -> new VertxHttpUserApi(newHttpClient(), baseUrl, authentication));
    }

}
