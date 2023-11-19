package com.chavaillaz.jenkins.client;

import com.chavaillaz.client.Client;

public interface JenkinsClient extends Client<JenkinsClient> {

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

}
