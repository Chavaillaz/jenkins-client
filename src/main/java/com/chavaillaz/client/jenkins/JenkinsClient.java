package com.chavaillaz.client.jenkins;

import com.chavaillaz.client.common.Client;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.api.QueueApi;
import com.chavaillaz.client.jenkins.api.StatisticsApi;
import com.chavaillaz.client.jenkins.api.SystemApi;
import com.chavaillaz.client.jenkins.api.UserApi;
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
     * Gets the statistics client
     *
     * @return The statistics client
     */
    StatisticsApi getStatisticsApi();

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
