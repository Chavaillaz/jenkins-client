package com.chavaillaz.client.jenkins;

import static io.vertx.core.Vertx.vertx;

import com.chavaillaz.client.common.Client;
import com.chavaillaz.client.jenkins.apache.ApacheHttpJenkinsClient;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.api.UserApi;
import com.chavaillaz.client.jenkins.java.JavaHttpJenkinsClient;
import com.chavaillaz.client.jenkins.okhttp.OkHttpJenkinsClient;
import com.chavaillaz.client.jenkins.vertx.VertxHttpJenkinsClient;

public interface JenkinsClient extends Client<JenkinsClient> {

    String COOKIE_JSESSIONID = "JSESSIONID";

    /**
     * Creates a default {@link JenkinsClient} with Apache HTTP client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    static ApacheHttpJenkinsClient apacheClient(String jenkinsUrl) {
        return new ApacheHttpJenkinsClient(jenkinsUrl);
    }

    /**
     * Creates a default {@link JenkinsClient} with Java HTTP client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    static JavaHttpJenkinsClient javaClient(String jenkinsUrl) {
        return new JavaHttpJenkinsClient(jenkinsUrl);
    }

    /**
     * Creates a default {@link JenkinsClient} with OkHttp client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    static OkHttpJenkinsClient okHttpClient(String jenkinsUrl) {
        return new OkHttpJenkinsClient(jenkinsUrl);
    }

    /**
     * Creates a default {@link JenkinsClient} with Vert.x HTTP client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    static VertxHttpJenkinsClient vertxClient(String jenkinsUrl) {
        return new VertxHttpJenkinsClient(vertx(), jenkinsUrl);
    }

    /**
     * Gets the job client.
     *
     * @return The job client
     */
    JobApi getJobApi();

    /**
     * Gets the pipeline client.
     *
     * @return The pipeline client
     */
    PipelineApi getPipelineApi();

    /**
     * Gets the plugin client.
     *
     * @return The plugin client
     */
    PluginApi getPluginApi();

    /**
     * Ges the queue client.
     *
     * @return The queue client
     */
    QueueApi getQueueApi();

    /**
     * Gets the system client
     *
     * @return The system client
     */
    SystemApi getSystemApi();

    /**
     * Gets the user client
     *
     * @return THe user client
     */
    UserApi getUserApi();

    /**
     * Sets the credentials to use for all requests to the API.
     *
     * @param username The username
     * @param token    The token
     * @return The current client instance
     */
    JenkinsClient withTokenAuthentication(String username, String token);

}
