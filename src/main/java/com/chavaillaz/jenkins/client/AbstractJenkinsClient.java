package com.chavaillaz.jenkins.client;

import static com.chavaillaz.jenkins.client.Authentication.AuthenticationType.ANONYMOUS;
import static com.chavaillaz.jenkins.client.Authentication.AuthenticationType.PASSWORD;
import static com.chavaillaz.jenkins.client.Authentication.AuthenticationType.TOKEN;

import com.chavaillaz.jenkins.utility.ProxyConfiguration;

public abstract class AbstractJenkinsClient<C> implements JenkinsClient {

    protected final String jenkinsUrl;
    protected Authentication authentication;
    protected ProxyConfiguration proxy;

    protected JobClient cacheJobClient;
    protected PipelineClient cachePipelineClient;
    protected PluginClient cachePluginClient;
    protected QueueClient cacheQueueClient;
    protected StatisticsClient cacheStatisticsClient;
    protected SystemClient cacheSystemClient;
    protected UserClient cacheUserClient;

    /**
     * Creates a new Jenkins client.
     *
     * @param jenkinsUrl The URL of the Jenkins server to query in this client
     */
    protected AbstractJenkinsClient(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
        withAnonymousAuthentication();
    }

    /**
     * Creates a new HTTP client that will be used to communicate with Jenkins REST endpoints.
     *
     * @return The HTTP client
     */
    protected abstract C newHttpClient();

    @Override
    public JenkinsClient withProxy(String host, Integer port) {
        this.proxy = ProxyConfiguration.from(host, port);
        return this;
    }

    @Override
    public JenkinsClient withProxy(String url) {
        this.proxy = ProxyConfiguration.from(url);
        return this;
    }

    @Override
    public JenkinsClient withTokenAuthentication(String username, String token) {
        this.authentication = new Authentication(TOKEN, username, token);
        return this;
    }

    @Override
    public JenkinsClient withUserAuthentication(String username, String password) {
        this.authentication = new Authentication(PASSWORD, username, password);
        return this;
    }

    @Override
    public JenkinsClient withAnonymousAuthentication() {
        this.authentication = new Authentication(ANONYMOUS);
        return this;
    }

}
