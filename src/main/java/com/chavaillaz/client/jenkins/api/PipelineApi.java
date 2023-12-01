package com.chavaillaz.client.jenkins.api;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.domain.folder.Path;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Actions;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Artifacts;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Node;
import com.chavaillaz.client.jenkins.domain.job.pipeline.NodeLog;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Run;
import com.chavaillaz.client.jenkins.domain.job.pipeline.Runs;

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
    CompletableFuture<Runs> getRunHistory(Path path, String pipelineName, String branchName);

    /**
     * Gets the details of the given pipeline run.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @return A {@link CompletableFuture} with the run
     */
    CompletableFuture<Run> getRun(Path path, String pipelineName, String branchName, long buildNumber);

    /**
     * Gets the pending actions of the given pipeline run.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @return A {@link CompletableFuture} with the pending actions
     */
    CompletableFuture<Actions> getRunPendingActions(Path path, String pipelineName, String branchName, long buildNumber);

    /**
     * Gets the artifacts of the given pipeline run.
     *
     * @param path         The folders path
     * @param pipelineName The pipeline name
     * @param branchName   The branch name
     * @param buildNumber  The build number
     * @return A {@link CompletableFuture} with the artifacts
     */
    CompletableFuture<Artifacts> getRunArtifacts(Path path, String pipelineName, String branchName, long buildNumber);

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
    CompletableFuture<Node> getRunNode(Path path, String pipelineName, String branchName, long buildNumber, String nodeId);

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
    CompletableFuture<NodeLog> getRunNodeLog(Path path, String pipelineName, String branchName, long buildNumber, String nodeId);

}
