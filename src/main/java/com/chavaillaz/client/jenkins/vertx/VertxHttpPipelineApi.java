package com.chavaillaz.client.jenkins.vertx;

import static io.vertx.core.http.HttpMethod.GET;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.domain.Path;
import com.chavaillaz.client.jenkins.domain.PipelineActions;
import com.chavaillaz.client.jenkins.domain.PipelineArtifacts;
import com.chavaillaz.client.jenkins.domain.PipelineNode;
import com.chavaillaz.client.jenkins.domain.PipelineNodeLog;
import com.chavaillaz.client.jenkins.domain.PipelineRun;
import com.chavaillaz.client.jenkins.domain.PipelineRuns;

import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link PipelineApi} for Vert.x HTTP.
 */
public class VertxHttpPipelineApi extends AbstractVertxHttpClient implements PipelineApi {

    /**
     * Creates a new pipeline client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public VertxHttpPipelineApi(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<PipelineRuns> getRunHistory(Path path, String pipelineName, String branchName) {
        return handleAsync(requestBuilder(GET, URL_RUN_HISTORY, path, pipelineName, branchName).send(), PipelineRuns.class);
    }

    @Override
    public CompletableFuture<PipelineRun> getRun(Path path, String pipelineName, String branchName, long buildNumber) {
        return handleAsync(requestBuilder(GET, URL_RUN_DETAILS, path, pipelineName, branchName, buildNumber).send(), PipelineRun.class);
    }

    @Override
    public CompletableFuture<PipelineActions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber) {
        return handleAsync(requestBuilder(GET, URL_RUN_ACTIONS, path, pipelineName, branchName, buildNumber).send(), PipelineActions.class);
    }

    @Override
    public CompletableFuture<PipelineArtifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber) {
        return handleAsync(requestBuilder(GET, URL_RUN_ARTIFACTS, path, pipelineName, branchName, buildNumber).send(), PipelineArtifacts.class);
    }

    @Override
    public CompletableFuture<PipelineNode> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return handleAsync(requestBuilder(GET, URL_RUN_STAGE_DETAILS, path, pipelineName, branchName, buildNumber, nodeId).send(), PipelineNode.class);
    }

    @Override
    public CompletableFuture<PipelineNodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return handleAsync(requestBuilder(GET, URL_RUN_STAGE_LOGS, path, pipelineName, branchName, buildNumber, nodeId).send(), PipelineNodeLog.class);
    }

}
