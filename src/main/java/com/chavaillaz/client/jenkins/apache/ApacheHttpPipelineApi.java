package com.chavaillaz.client.jenkins.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.PipelineApi;
import com.chavaillaz.client.jenkins.domain.Path;
import com.chavaillaz.client.jenkins.domain.PipelineActions;
import com.chavaillaz.client.jenkins.domain.PipelineArtifacts;
import com.chavaillaz.client.jenkins.domain.PipelineNode;
import com.chavaillaz.client.jenkins.domain.PipelineNodeLog;
import com.chavaillaz.client.jenkins.domain.PipelineRun;
import com.chavaillaz.client.jenkins.domain.PipelineRuns;

/**
 * Implementation of {@link PipelineApi} for Apache HTTP.
 */
public class ApacheHttpPipelineApi extends AbstractApacheHttpClient implements PipelineApi {

    /**
     * Creates a new pipeline client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public ApacheHttpPipelineApi(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<PipelineRuns> getRunHistory(Path path, String pipelineName, String branchName) {
        return sendAsync(requestBuilder(get(), URL_RUN_HISTORY, path, pipelineName, branchName), PipelineRuns.class);
    }

    @Override
    public CompletableFuture<PipelineRun> getRun(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(get(), URL_RUN_DETAILS, path, pipelineName, branchName, buildNumber), PipelineRun.class);
    }

    @Override
    public CompletableFuture<PipelineActions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(get(), URL_RUN_ACTIONS, path, pipelineName, branchName, buildNumber), PipelineActions.class);
    }

    @Override
    public CompletableFuture<PipelineArtifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(get(), URL_RUN_ARTIFACTS, path, pipelineName, branchName, buildNumber), PipelineArtifacts.class);
    }

    @Override
    public CompletableFuture<PipelineNode> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(get(), URL_RUN_STAGE_DETAILS, path, pipelineName, branchName, buildNumber, nodeId), PipelineNode.class);
    }

    @Override
    public CompletableFuture<PipelineNodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(get(), URL_RUN_STAGE_LOGS, path, pipelineName, branchName, buildNumber, nodeId), PipelineNodeLog.class);
    }

}
