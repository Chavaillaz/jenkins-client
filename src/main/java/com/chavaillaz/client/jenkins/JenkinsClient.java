package com.chavaillaz.client.jenkins;

import com.chavaillaz.client.common.Client;
import com.chavaillaz.client.jenkins.api.JobClient;
import com.chavaillaz.client.jenkins.api.PipelineClient;
import com.chavaillaz.client.jenkins.api.PluginClient;
import com.chavaillaz.client.jenkins.api.QueueClient;
import com.chavaillaz.client.jenkins.api.StatisticsClient;
import com.chavaillaz.client.jenkins.api.SystemClient;
import com.chavaillaz.client.jenkins.api.UserClient;
import com.chavaillaz.client.jenkins.java.JavaHttpJenkinsClient;

public interface JenkinsClient extends Client<JenkinsClient> {

    String SET_COOKIE = "Set-Cookie";
    String JENKINS_COOKIES_JSESSIONID = "JSESSIONID";

    /**
     * Creates a default {@link JenkinsClient} with Java HTTP client.
     *
     * @param jenkinsUrl The Jenkins URL
     */
    static JavaHttpJenkinsClient javaClient(String jenkinsUrl) {
        return new JavaHttpJenkinsClient(jenkinsUrl);
    }

    /**
     * Gets the job client.
     *
     * @return The job client
     */
    JobClient getJobClient();

    /**
     * Gets the pipeline client.
     *
     * @return The pipeline client
     */
    PipelineClient getPipelineClient();

    /**
     * Gets the plugin client.
     *
     * @return The plugin client
     */
    PluginClient getPluginClient();

    /**
     * Ges the queue client.
     *
     * @return The queue client
     */
    QueueClient getQueueClient();

    /**
     * Gets the statistics client
     *
     * @return The statistics client
     */
    StatisticsClient getStatisticsClient();

    /**
     * Gets the system client
     *
     * @return The system client
     */
    SystemClient getSystemClient();

    /**
     * Gets the user client
     *
     * @return THe user client
     */
    UserClient getUserClient();

    /**
     * Sets the credentials to use for all requests to the API.
     *
     * @param username The username
     * @param token    The token
     * @return The current client instance
     */
    JenkinsClient withTokenAuthentication(String username, String token);

}
