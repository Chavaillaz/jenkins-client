package com.chavaillaz.client.jenkins.java;

import java.net.http.HttpClient;
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

/**
 * Implementation of {@link PipelineApi} for Java HTTP.
 */
public class JavaHttpPipelineApi extends AbstractJavaHttpClient implements PipelineApi {

    /**
     * Creates a new pipeline client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpPipelineApi(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Runs> getRunHistory(Path path, String pipelineName, String branchName) {
        return sendAsync(requestBuilder(URL_RUN_HISTORY, path, pipelineName, branchName).GET(), Runs.class);
    }

    @Override
    public CompletableFuture<Run> getRun(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_DETAILS, path, pipelineName, branchName, buildNumber).GET(), Run.class);
    }

    @Override
    public CompletableFuture<Actions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_ACTIONS, path, pipelineName, branchName, buildNumber).GET(), Actions.class);
    }

    @Override
    public CompletableFuture<Artifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber) {
        return sendAsync(requestBuilder(URL_RUN_ARTIFACTS, path, pipelineName, branchName, buildNumber).GET(), Artifacts.class);
    }

    @Override
    public CompletableFuture<Node> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(URL_RUN_STAGE_DETAILS, path, pipelineName, branchName, buildNumber, nodeId).GET(), Node.class);
    }

    @Override
    public CompletableFuture<NodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId) {
        return sendAsync(requestBuilder(URL_RUN_STAGE_LOGS, path, pipelineName, branchName, buildNumber, nodeId).GET(), NodeLog.class);
    }

}
