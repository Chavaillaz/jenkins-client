package com.chavaillaz.jenkins.client;

import static com.chavaillaz.client.Authentication.AuthenticationType.PASSWORD;
import static com.chavaillaz.client.Authentication.AuthenticationType.TOKEN;

import com.chavaillaz.client.AbstractClient;
import com.chavaillaz.client.utility.LazyCachedObject;

/**
 * Abstract class implementing common parts for Jenkins clients.
 *
 * @param <C> The HTTP client type
 */
public abstract class AbstractJenkinsClient<C> extends AbstractClient<C, JenkinsAuthentication, JenkinsClient> implements JenkinsClient {

    protected LazyCachedObject<JobClient> cacheJobClient = new LazyCachedObject<>();
    protected LazyCachedObject<PipelineClient> cachePipelineClient = new LazyCachedObject<>();
    protected LazyCachedObject<PluginClient> cachePluginClient = new LazyCachedObject<>();
    protected LazyCachedObject<QueueClient> cacheQueueClient = new LazyCachedObject<>();
    protected LazyCachedObject<StatisticsClient> cacheStatisticsClient = new LazyCachedObject<>();
    protected LazyCachedObject<SystemClient> cacheSystemClient = new LazyCachedObject<>();
    protected LazyCachedObject<UserClient> cacheUserClient = new LazyCachedObject<>();

    /**
     * Creates a new abstract client.
     *
     * @param baseUrl The base URL of the endpoints
     */
    protected AbstractJenkinsClient(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public JenkinsClient withTokenAuthentication(String username, String token) {
        this.authentication = new JenkinsAuthentication(TOKEN, username, token);
        return this;
    }

    @Override
    public JenkinsClient withUserAuthentication(String username, String password) {
        this.authentication = new JenkinsAuthentication(PASSWORD, username, password);
        return this;
    }

    @Override
    public JenkinsClient withAnonymousAuthentication() {
        this.authentication = new JenkinsAuthentication();
        return this;
    }

}
