package com.chavaillaz.client.jenkins.api;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.domain.system.Load;

public interface StatisticsApi {

    String URL_STATISTICS = "/overallLoad/api/json";

    /**
     * Gets the overall load for the entire system (the master and all the agents combined) and all the jobs that are running on it.
     *
     * @return A {@link CompletableFuture} with the load
     */
    CompletableFuture<Load> getOverallLoad();

}
