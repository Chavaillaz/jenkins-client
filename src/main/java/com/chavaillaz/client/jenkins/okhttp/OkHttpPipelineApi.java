package com.chavaillaz.client.jenkins.okhttp;

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

import okhttp3.OkHttpClient;

/**
 * Implementation of {@link PipelineApi} for OkHttp.
 */
public class OkHttpPipelineApi extends AbstractOkHttpClient implements PipelineApi {

    /**
     * Creates a new pipeline client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public OkHttpPipelineApi(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<PipelineRuns> getRunHistory(Path path, String pipelineName, String branchName) {
        return sendAsync(requestBuilder(URL_RUN_HISTORY, path, pipelineName, branchName).get(), PipelineRuns.class);
    }

    @Override
    public CompletableFuture<PipelineRun> getRun(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_DETAILS, path, pipelineName, branchName, buildNumber).get(), PipelineRun.class);
    }

    @Override
    public CompletableFuture<PipelineActions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_ACTIONS, path, pipelineName, branchName, buildNumber).get(), PipelineActions.class);
    }

    @Override
    public CompletableFuture<PipelineArtifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_ARTIFACTS, path, pipelineName, branchName, buildNumber).get(), PipelineArtifacts.class);
    }

    @Override
    public CompletableFuture<PipelineNode> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(URL_RUN_STAGE_DETAILS, path, pipelineName, branchName, buildNumber, nodeId).get(), PipelineNode.class);
    }

    @Override
    public CompletableFuture<PipelineNodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(URL_RUN_STAGE_LOGS, path, pipelineName, branchName, buildNumber, nodeId).get(), PipelineNodeLog.class);
    }

}
