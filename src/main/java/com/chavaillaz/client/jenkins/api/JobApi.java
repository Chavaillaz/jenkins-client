package com.chavaillaz.client.jenkins.api;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.domain.BuildInfo;
import com.chavaillaz.client.jenkins.domain.CoverageReport;
import com.chavaillaz.client.jenkins.domain.Folder;
import com.chavaillaz.client.jenkins.domain.JobInfo;
import com.chavaillaz.client.jenkins.domain.Path;
import com.chavaillaz.client.jenkins.domain.TestReport;
import com.chavaillaz.client.jenkins.domain.ViewInfo;

public interface JobApi {

    String URL_FOLDER = "{0}/api/json";
    String URL_FOLDER_CONFIGURATION = "{0}/config.xml";
    String URL_FOLDER_CREATE = "{0}/createItem?name={1}&mode={2}";
    String URL_FOLDER_RENAME = "{0}/job/{1}/confirmRename?newName={2}";
    String URL_FOLDER_DELETE = "{0}/job/{1}/doDelete";
    String URL_VIEW_CONFIGURATION = "{0}/view/{1}/config.xml";
    String URL_VIEW_INFO = "{0}/view/{1}/api/json";
    String URL_VIEW_CREATE = "{0}/createView";
    String URL_VIEW_DELETE = "{0}/view/{1}/doDelete";
    String URL_JOB_INFO = "{0}/job/{1}/api/json";
    String URL_JOB_DESCRIPTION = "{0}/job/{1}/description";
    String URL_JOB_CONFIGURATION = "{0}/job/{1}/config.xml";
    String URL_JOB_CREATE = "{0}/createItem?name={1}&mode=hudson.model.FreeStyleProject";
    String URL_JOB_RENAME = "{0}/job/{1}/confirmRename?newName={2}";
    String URL_JOB_ENABLE = "{0}/job/{1}/enable";
    String URL_JOB_DISABLE = "{0}/job/{1}/disable";
    String URL_JOB_DELETE = "{0}/job/{1}/doDelete";
    String URL_JOB_BUILD = "{0}/job/{1}/build";
    String URL_JOB_BUILD_PARAMETERS = "{0}/job/{1}/buildWithParameters?{2}";
    String URL_JOB_BUILD_INFO = "{0}/job/{1}/{2,number,#}/api/json";
    String URL_JOB_BUILD_CONSOLE = "{0}/job/{1}/{2,number,#}/logText/progressiveText?start={3}";
    String URL_JOB_BUILD_STOP = "{0}/job/{1}/{2,number,#}/stop";
    String URL_JOB_BUILD_ARTIFACT = "{0}/job/{1}/{2,number,#}/artifact/{3}";
    String URL_JOB_BUILD_TESTS = "{0}/job/{1}/{2,number,#}/testReport/api/json";
    String URL_JOB_BUILD_COVERAGE = "{0}/job/{1}/{2,number,#}/jacoco/";
    String URL_JOB_LAST_BUILD_NUMBER = "{0}/job/{1}/lastBuild/buildNumber";
    String URL_JOB_LAST_BUILD_CONSOLE = "{0}/job/{1}/lastBuild/logText/progressiveText?start={2}";

    /**
     * Gets the list of all the defined jobs, views and folders in the given folder on Jenkins.
     *
     * @param path The folders path
     * @return A {@link CompletableFuture} with the jobs
     */
    CompletableFuture<Folder> getFolder(Path path);

    /**
     * Gets the details of the given view.
     *
     * @param path     The folders path
     * @param viewName The view name
     * @return A {@link CompletableFuture} with the view
     */
    CompletableFuture<ViewInfo> getView(Path path, String viewName);

    /**
     * Gets the details of the given job.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} with the job
     */
    CompletableFuture<JobInfo> getJob(Path path, String jobName);

    /**
     * Gets the configuration of the given folder as XML.
     *
     * @param path The folders path
     * @return A {@link CompletableFuture} with the configuration
     */
    CompletableFuture<String> getFolderConfiguration(Path path);

    /**
     * Gets the configuration of the given job as XML.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} with the configuration
     */
    CompletableFuture<String> getJobConfiguration(Path path, String jobName);

    /**
     * Gets the configuration of the given view as XML.
     *
     * @param path     The folders path
     * @param viewName The view name
     * @return A {@link CompletableFuture} with the configuration
     */
    CompletableFuture<String> getViewConfiguration(Path path, String viewName);

    /**
     * Gets the description of the given job.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} with the description
     */
    CompletableFuture<String> getJobDescription(Path path, String jobName);

    /**
     * Gets the last build number of the given job.
     * Returns {@code null} in case the job has not been built yet.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} with the build number
     */
    CompletableFuture<Integer> getLastBuildNumber(Path path, String jobName);

    /**
     * Gets the details of the given build.
     *
     * @param path        The folders path
     * @param jobName     The job name
     * @param buildNumber The build number
     * @return A {@link CompletableFuture} with the build
     */
    CompletableFuture<BuildInfo> getBuild(Path path, String jobName, int buildNumber);

    /**
     * Gets an artifact of the given build.
     *
     * @param path         The folders path
     * @param jobName      The job name
     * @param buildNumber  The build number
     * @param artifactPath The relative path of the artifact
     * @return A {@link CompletableFuture} with the artifact
     */
    CompletableFuture<InputStream> getArtifact(Path path, String jobName, int buildNumber, String artifactPath);

    /**
     * Gets the test report of the given build.
     * Returns {@code null} in case a test report does not exist for this build.
     *
     * @param path        The folders path
     * @param jobName     The job name
     * @param buildNumber The build number
     * @return A {@link CompletableFuture} with the test report
     */
    CompletableFuture<TestReport> getTestReport(Path path, String jobName, int buildNumber);

    /**
     * Gets the test coverage report of the given build.
     * Returns {@code null} in case a coverage report does not exist for this build.
     *
     * @param path        The folders path
     * @param jobName     The job name
     * @param buildNumber The build number
     * @return A {@link CompletableFuture} with the test coverage report
     */
    CompletableFuture<CoverageReport> getTestCoverageReport(Path path, String jobName, int buildNumber);

    /**
     * Gets the text of the last build of the given job.
     *
     * @param path         The folders path
     * @param jobName      The job name
     * @param bufferOffset The offset from which start to get the text, {@code 0} to get everything
     * @return A {@link CompletableFuture} with the text
     */
    CompletableFuture<String> getConsoleOutput(Path path, String jobName, int bufferOffset);

    /**
     * Gets the text of given build of the given job.
     *
     * @param path         The folders path
     * @param jobName      The job name
     * @param buildNumber  The build number
     * @param bufferOffset The offset from which start to get the text, {@code 0} to get everything
     * @return A {@link CompletableFuture} with the text
     */
    CompletableFuture<String> getConsoleOutput(Path path, String jobName, int buildNumber, int bufferOffset);

    /**
     * Creates a new folder.
     *
     * @param path       The folders path
     * @param folderName The name of the folder to create
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> createFolder(Path path, String folderName);

    /**
     * Creates a new view in the given folder.
     *
     * @param path     The folders path
     * @param viewName The name of the view to create
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> createView(Path path, String viewName);

    /**
     * Creates a new job in the given folder.
     *
     * @param path      The folders path
     * @param jobName   The name of the job to create
     * @param configXML The configuration of the job as XML
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> createJob(Path path, String jobName, String configXML);

    /**
     * Updates the configuration of the given job.
     *
     * @param path      The folders path
     * @param jobName   The job name
     * @param configXML The configuration of the job as XML
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> updateJobConfiguration(Path path, String jobName, String configXML);

    /**
     * Updates the configuration of the given view.
     *
     * @param path      The folders path
     * @param viewName  The view name
     * @param configXML The configuration of the view as XML
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> updateViewConfiguration(Path path, String viewName, String configXML);

    /**
     * Renames the given folder.
     *
     * @param path       The folders path
     * @param folderName The folder old name
     * @param newName    The folder new name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> renameFolder(Path path, String folderName, String newName);

    /**
     * Renames the given job.
     *
     * @param path    The folders path
     * @param jobName The job old name
     * @param newName The job new name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> renameJob(Path path, String jobName, String newName);

    /**
     * Enables the given job.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> enableJob(Path path, String jobName);

    /**
     * Disables the given job.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> disableJob(Path path, String jobName);

    /**
     * Deletes the given folder.
     *
     * @param path       The folders path
     * @param folderName The folder name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteFolder(Path path, String folderName);

    /**
     * Deletes the given job.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteJob(Path path, String jobName);

    /**
     * Deletes the given view.
     *
     * @param path     The folders path
     * @param viewName The view name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> deleteView(Path path, String viewName);

    /**
     * Starts a build of the given job.
     *
     * @param path    The folders path
     * @param jobName The job name
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> buildJob(Path path, String jobName);

    /**
     * Builds a parameterized job with the given properties.
     *
     * @param path       The folders path
     * @param jobName    The job name
     * @param properties The build parameters
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> buildJob(Path path, String jobName, Map<Object, Object> properties);

    /**
     * Stops the given build.
     *
     * @param path        The folders path
     * @param jobName     The job name
     * @param buildNumber The build number
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> stopBuild(Path path, String jobName, int buildNumber);

}
