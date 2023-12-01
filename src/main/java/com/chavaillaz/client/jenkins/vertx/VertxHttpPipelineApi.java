package com.chavaillaz.client.jenkins.vertx;

import static io.vertx.core.http.HttpMethod.GET;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.domain.folder.Path;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Actions;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Artifacts;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Node;
import com.chavaillaz.client.jenkins.domain.job.pipeline.NodeLog;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Run;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Runs;
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
    public CompletableFuture<Runs> getRunHistory(Path path, String pipelineName, String branchName) {
        return handleAsync(requestBuilder(GET, URL_RUN_HISTORY, path, pipelineName, branchName).send(), Runs.class);
    }

    @Override
    public CompletableFuture<Run> getRun(Path path, String pipelineName, String branchName, long buildNumber) {
        return handleAsync(requestBuilder(GET, URL_RUN_DETAILS, path, pipelineName, branchName, buildNumber).send(), Run.class);
    }

    @Override
    public CompletableFuture<Actions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber) {
        return handleAsync(requestBuilder(GET, URL_RUN_ACTIONS, path, pipelineName, branchName, buildNumber).send(), Actions.class);
    }

    @Override
    public CompletableFuture<Artifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber) {
        return handleAsync(requestBuilder(GET, URL_RUN_ARTIFACTS, path, pipelineName, branchName, buildNumber).send(), Artifacts.class);
    }

    @Override
    public CompletableFuture<Node> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return handleAsync(requestBuilder(GET, URL_RUN_STAGE_DETAILS, path, pipelineName, branchName, buildNumber, nodeId).send(), Node.class);
    }

    @Override
    public CompletableFuture<NodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return handleAsync(requestBuilder(GET, URL_RUN_STAGE_LOGS, path, pipelineName, branchName, buildNumber, nodeId).send(), NodeLog.class);
    }

}
