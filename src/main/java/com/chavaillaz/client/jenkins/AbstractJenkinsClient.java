package com.chavaillaz.client.jenkins;


import com.chavaillaz.client.common.AbstractClient;
import com.chavaillaz.client.common.utility.LazyCachedObject;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.api.UserApi;

/**
 * Abstract class implementing common parts for Jenkins clients.
 *
 * @param <C> The HTTP client type
 */
public abstract class AbstractJenkinsClient<C> extends AbstractClient<C, JenkinsClient> implements JenkinsClient {

    protected JenkinsAuthentication authentication;

    protected LazyCachedObject<JobApi> jobApi = new LazyCachedObject<>();
    protected LazyCachedObject<PipelineApi> pipelineApi = new LazyCachedObject<>();
    protected LazyCachedObject<PluginApi> pluginApi = new LazyCachedObject<>();
    protected LazyCachedObject<QueueApi> queueApi = new LazyCachedObject<>();
    protected LazyCachedObject<StatisticsApi> statisticsApi = new LazyCachedObject<>();
    protected LazyCachedObject<SystemApi> systemApi = new LazyCachedObject<>();
    protected LazyCachedObject<UserApi> userApi = new LazyCachedObject<>();

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
