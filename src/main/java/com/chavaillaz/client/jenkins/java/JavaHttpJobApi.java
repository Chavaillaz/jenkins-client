package com.chavaillaz.client.jenkins.java;

import static com.chavaillaz.client.common.java.JavaHttpUtils.ofFormData;
import static com.chavaillaz.client.common.utility.Utils.queryFromKeyValue;
import static com.chavaillaz.client.jenkins.JenkinsConstant.FOLDER_MODE;
import static com.chavaillaz.client.jenkins.JenkinsConstant.LIST_VIEW;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.utility.Utils;
import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.domain.folder.Folder;
import com.chavaillaz.client.jenkins.domain.folder.Path;
import com.chavaillaz.client.jenkins.domain.job.JobInfo;
import com.chavaillaz.client.jenkins.domain.job.build.BuildInfo;
import com.chavaillaz.client.jenkins.domain.job.test.CoverageReport;
import com.chavaillaz.client.jenkins.domain.job.test.TestReport;
import com.chavaillaz.client.jenkins.domain.view.ViewCreation;
import com.chavaillaz.client.jenkins.domain.view.ViewInfo;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Implementation of {@link JobApi} for Java HTTP.
 */
public class JavaHttpJobApi extends AbstractJavaHttpClient implements JobApi {

    /**
     * Creates a new job client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpJobApi(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Folder> getFolder(Path path) {
        return sendAsync(requestBuilder(URL_FOLDER, path).GET(), Folder.class);
    }

    @Override
    public CompletableFuture<ViewInfo> getView(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_INFO, path, viewName).GET(), ViewInfo.class);
    }

    @Override
    public CompletableFuture<JobInfo> getJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_INFO, path, jobName).GET(), JobInfo.class);
    }

    @Override
    public CompletableFuture<String> getFolderConfiguration(Path path) {
        return sendAsync(requestBuilder(URL_FOLDER_CONFIGURATION, path).GET())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobConfiguration(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_CONFIGURATION, path, jobName).GET())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getViewConfiguration(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_CONFIGURATION, path, viewName).GET())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobDescription(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_DESCRIPTION, path, jobName).GET())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Integer> getLastBuildNumber(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_LAST_BUILD_NUMBER, path, jobName).GET())
                .thenApply(Utils::readInputStream)
                .thenApply(NumberUtils::toInt)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<BuildInfo> getBuild(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_INFO, path, jobName, buildNumber).GET(), BuildInfo.class);
    }

    @Override
    public CompletableFuture<InputStream> getArtifact(Path path, String jobName, int buildNumber, String artifactPath) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_ARTIFACT, path, jobName, buildNumber, artifactPath).GET());
    }

    @Override
    public CompletableFuture<TestReport> getTestReport(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_TESTS, path, jobName, buildNumber).GET(), TestReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<CoverageReport> getTestCoverageReport(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_COVERAGE, path, jobName, buildNumber).GET(), CoverageReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int bufferOffset) {
        return sendAsync(requestBuilder(URL_JOB_LAST_BUILD_CONSOLE, path, jobName, bufferOffset).GET())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int buildNumber, int bufferOffset) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_CONSOLE, path, jobName, buildNumber, bufferOffset).GET())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Void> createFolder(Path path, String folderName) {
        return sendAsync(requestBuilder(URL_FOLDER_CREATE, path, folderName, FOLDER_MODE).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> createJob(Path path, String jobName, String configXML) {
        return sendAsync(requestBuilder(URL_JOB_CREATE, path, jobName)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .POST(ofString(configXML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> createView(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_CREATE, path)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .POST(ofFormData(Map.of(
                        "name", viewName,
                        "mode", LIST_VIEW,
                        "json", serialize(new ViewCreation(viewName, LIST_VIEW))
                ))), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateJobConfiguration(Path path, String jobName, String configXML) {
        return sendAsync(requestBuilder(URL_JOB_CONFIGURATION, path, jobName)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .POST(ofString(configXML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateViewConfiguration(Path path, String viewName, String configXML) {
        return sendAsync(requestBuilder(URL_VIEW_CONFIGURATION, path, viewName)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .POST(ofString(configXML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameFolder(Path path, String folderName, String newName) {
        return sendAsync(requestBuilder(URL_FOLDER_RENAME, path, folderName, newName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameJob(Path path, String jobName, String newName) {
        return sendAsync(requestBuilder(URL_JOB_RENAME, path, jobName, newName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> enableJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_ENABLE, path, jobName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> disableJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_DISABLE, path, jobName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteFolder(Path path, String folderName) {
        return sendAsync(requestBuilder(URL_FOLDER_DELETE, path, folderName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_DELETE, path, jobName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteView(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_DELETE, path, viewName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_BUILD, path, jobName).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName, Map<Object, Object> properties) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_PARAMETERS, path, jobName, queryFromKeyValue(properties)).POST(noBody()), Void.class);
    }

    @Override
    public CompletableFuture<Void> stopBuild(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_STOP, path, jobName, buildNumber).POST(noBody()), Void.class);
    }

}
