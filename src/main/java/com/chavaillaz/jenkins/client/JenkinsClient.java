package com.chavaillaz.jenkins.client;

public interface JenkinsClient {

    String SET_COOKIE = "Set-Cookie";
    String JENKINS_COOKIES_JSESSIONID = "JSESSIONID";

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
     * Sets the proxy to use for all requests to the Jenkins API.
     *
     * @param host The proxy host
     * @param port The proxy port
     * @return The current client instance
     */
    JenkinsClient withProxy(String host, Integer port);

    /**
     * Sets the proxy to use for all requests to the Jenkins API.
     *
     * @param url The proxy URL
     * @return The current client instance
     */
    JenkinsClient withProxy(String url);

    /**
     * Sets the credentials to use for all requests to the Jenkins API.
     *
     * @param username The username
     * @param token    The personal access token
     * @return The current client instance
     */
    JenkinsClient withTokenAuthentication(String username, String token);

    /**
     * Sets the credentials to use for all requests to the Jenkins API.
     *
     * @param username The username
     * @param password The password
     * @return The current client instance
     */
    JenkinsClient withUserAuthentication(String username, String password);

    /**
     * Uses the anonymous access if available for all requests to the Jenkins API.
     *
     * @return The current client instance
     */
    JenkinsClient withAnonymousAuthentication();

}
