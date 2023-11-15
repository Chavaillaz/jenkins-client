package com.chavaillaz.jenkins.client.java;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jenkins.client.Authentication;
import com.chavaillaz.jenkins.client.PipelineClient;
import com.chavaillaz.jenkins.domain.Path;
import com.chavaillaz.jenkins.domain.PipelineActions;
import com.chavaillaz.jenkins.domain.PipelineArtifacts;
import com.chavaillaz.jenkins.domain.PipelineNode;
import com.chavaillaz.jenkins.domain.PipelineNodeLog;
import com.chavaillaz.jenkins.domain.PipelineRun;
import com.chavaillaz.jenkins.domain.PipelineRuns;

public class JavaHttpPipelineClient extends AbstractJavaHttpClient implements PipelineClient {

    /**
     * Creates a new pipeline client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpPipelineClient(HttpClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<PipelineRuns> getRunHistory(Path path, String pipelineName, String branchName) {
        return sendAsync(requestBuilder(URL_RUN_HISTORY, path, pipelineName, branchName).GET(), PipelineRuns.class);
    }

    @Override
    public CompletableFuture<PipelineRun> getRun(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_DETAILS, path, pipelineName, branchName, buildNumber).GET(), PipelineRun.class);
    }

    @Override
    public CompletableFuture<PipelineActions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_ACTIONS, path, pipelineName, branchName, buildNumber).GET(), PipelineActions.class);
    }

    @Override
    public CompletableFuture<PipelineArtifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_ARTIFACTS, path, pipelineName, branchName, buildNumber).GET(), PipelineArtifacts.class);
    }

    @Override
    public CompletableFuture<PipelineNode> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(URL_RUN_STAGE_DETAILS, path, pipelineName, branchName, buildNumber, nodeId).GET(), PipelineNode.class);
    }

    @Override
    public CompletableFuture<PipelineNodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(URL_RUN_STAGE_LOGS, path, pipelineName, branchName, buildNumber, nodeId).GET(), PipelineNodeLog.class);
    }

}
