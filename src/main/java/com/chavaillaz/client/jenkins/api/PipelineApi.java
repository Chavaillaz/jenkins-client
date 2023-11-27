package com.chavaillaz.client.jenkins.api;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.domain.Path;
import com.chavaillaz.client.jenkins.domain.PipelineActions;
import com.chavaillaz.client.jenkins.domain.PipelineArtifacts;
import com.chavaillaz.client.jenkins.domain.PipelineNode;
import com.chavaillaz.client.jenkins.domain.PipelineNodeLog;
import com.chavaillaz.client.jenkins.domain.PipelineRun;
import com.chavaillaz.client.jenkins.domain.PipelineRuns;

public interface PipelineApi {

    String URL_RUN_HISTORY = "{0}/job/{1}/job/{2}/wfapi/runs";
    String URL_RUN_DETAILS = "{0}/job/{1}/job/{2}/{3,number,#}/wfapi/describe";
    String URL_RUN_ACTIONS = "{0}/job/{1}/job/{2}/{3,number,#}/wfapi/pendingInputActions";
    String URL_RUN_ARTIFACTS = "{0}/job/{1}/job/{2}/{3,number,#}/wfapi/artifacts";
    String URL_RUN_STAGE_DETAILS = "{0}/job/{1}/job/{2}/{3,number,#}/execution/node/{4}/wfapi/describe";
    String URL_RUN_STAGE_LOGS = "{0}/job/{1}/job/{2}/{3,number,#}/execution/node/{4}/wfapi/log";

    /**
     * Gets the list of runs for a given pipeline.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @return A {@link CompletableFuture} with the runs
     */
    CompletableFuture<PipelineRuns> getRunHistory(Path path, String pipelineName, String branchName);

    /**
     * Gets the details of the given pipeline run.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @return A {@link CompletableFuture} with the run
     */
    CompletableFuture<PipelineRun> getRun(Path path, String pipelineName, String branchName, long buildNumber);

    /**
     * Gets the pending actions of the given pipeline run.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @return A {@link CompletableFuture} with the pending actions
     */
    CompletableFuture<PipelineActions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber);

    /**
     * Gets the artifacts of the given pipeline run.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @return A {@link CompletableFuture} with the artifacts
     */
    CompletableFuture<PipelineArtifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber);

    /**
     * Gets details of the given pipeline node.
     * If the node is a stage step, the response will have a list of all the pipeline nodes that were active during that stage, ordered by start time.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @param nodeId       The node identifier
     * @return A {@link CompletableFuture} with the run node
     */
    CompletableFuture<PipelineNode> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId);

    /**
     * Gets the logs of the given pipeline node.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @param nodeId       The node identifier
     * @return A {@link CompletableFuture} with the run node log
     */
    CompletableFuture<PipelineNodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId);

}
