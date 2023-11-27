package com.chavaillaz.client.jenkins;


import com.chavaillaz.client.common.AbstractClient;
import com.chavaillaz.client.common.utility.LazyCachedObject;
import com.chavaillaz.client.jenkins.api.JobClient;
import com.chavaillaz.client.jenkins.api.PipelineClient;
import com.chavaillaz.client.jenkins.api.PluginClient;
import com.chavaillaz.client.jenkins.api.QueueClient;
import com.chavaillaz.client.jenkins.api.StatisticsClient;
import com.chavaillaz.client.jenkins.api.SystemClient;
import com.chavaillaz.client.jenkins.api.UserClient;

/**
 * Abstract class implementing common parts for Jenkins clients.
 *
 * @param <C> The HTTP client type
 */
public abstract class AbstractJenkinsClient<C> extends AbstractClient<C, JenkinsClient> implements JenkinsClient {

    protected JenkinsAuthentication authentication;

    protected LazyCachedObject<JobClient> jobClient = new LazyCachedObject<>();
    protected LazyCachedObject<PipelineClient> pipelineClient = new LazyCachedObject<>();
    protected LazyCachedObject<PluginClient> pluginClient = new LazyCachedObject<>();
    protected LazyCachedObject<QueueClient> queueClient = new LazyCachedObject<>();
    protected LazyCachedObject<StatisticsClient> statisticsClient = new LazyCachedObject<>();
    protected LazyCachedObject<SystemClient> systemClient = new LazyCachedObject<>();
    protected LazyCachedObject<UserClient> userClient = new LazyCachedObject<>();

    /**
     * Creates a new abstract client.
     *
     * @param baseUrl The base URL of the endpoints
     */
    protected AbstractJenkinsClient(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public JenkinsClient withTokenAuthentication(String token) {
        throw new UnsupportedOperationException("Token authentication needs username");
    }

    @Override
    public JenkinsClient withTokenAuthentication(String username, String token) {
        this.authentication = new JenkinsAuthentication(username, token);
        return this;
    }

    @Override
    public JenkinsClient withUserAuthentication(String username, String password) {
        this.authentication = new JenkinsAuthentication(username, password);
        return this;
    }

    @Override
    public JenkinsClient withAnonymousAuthentication() {
        this.authentication = new JenkinsAuthentication(null, null);
        return this;
    }

}
