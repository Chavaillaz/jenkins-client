package com.chavaillaz.client.jenkins.apache;

import static com.chavaillaz.client.common.utility.Utils.queryFromKeyValue;
import static com.chavaillaz.client.jenkins.JenkinsConstant.FOLDER_MODE;
import static com.chavaillaz.client.jenkins.JenkinsConstant.LIST_VIEW;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.core5.http.ContentType.APPLICATION_XML;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

import com.chavaillaz.client.common.utility.Utils;
import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.JobApi;
import com.chavaillaz.client.jenkins.domain.BuildInfo;
import com.chavaillaz.client.jenkins.domain.CoverageReport;
import com.chavaillaz.client.jenkins.domain.Folder;
import com.chavaillaz.client.jenkins.domain.JobInfo;
import com.chavaillaz.client.jenkins.domain.Path;
import com.chavaillaz.client.jenkins.domain.TestReport;
import com.chavaillaz.client.jenkins.domain.ViewInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Implementation of {@link JobApi} for Apache HTTP.
 */
public class ApacheHttpJobApi extends AbstractApacheHttpClient implements JobApi {

    /**
     * Creates a new job client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public ApacheHttpJobApi(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Folder> getFolder(Path path) {
        return sendAsync(requestBuilder(get(), URL_FOLDER, path), Folder.class);
    }

    @Override
    public CompletableFuture<ViewInfo> getView(Path path, String viewName) {
        return sendAsync(requestBuilder(get(), URL_VIEW_INFO, path, viewName), ViewInfo.class);
    }

    @Override
    public CompletableFuture<JobInfo> getJob(Path path, String jobName) {
        return sendAsync(requestBuilder(get(), URL_JOB_INFO, path, jobName), JobInfo.class);
    }

    @Override
    public CompletableFuture<String> getFolderConfiguration(Path path) {
        return sendAsync(requestBuilder(get(), URL_FOLDER_CONFIGURATION, path))
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobConfiguration(Path path, String jobName) {
        return sendAsync(requestBuilder(get(), URL_JOB_CONFIGURATION, path, jobName))
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getViewConfiguration(Path path, String viewName) {
        return sendAsync(requestBuilder(get(), URL_VIEW_CONFIGURATION, path, viewName))
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobDescription(Path path, String jobName) {
        return sendAsync(requestBuilder(get(), URL_JOB_DESCRIPTION, path, jobName))
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Integer> getLastBuildNumber(Path path, String jobName) {
        return sendAsync(requestBuilder(get(), URL_JOB_LAST_BUILD_NUMBER, path, jobName))
                .thenApply(Utils::readInputStream)
                .thenApply(NumberUtils::toInt)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<BuildInfo> getBuild(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(get(), URL_JOB_BUILD_INFO, path, jobName, buildNumber), BuildInfo.class);
    }

    @Override
    public CompletableFuture<InputStream> getArtifact(Path path, String jobName, int buildNumber, String artifactPath) {
        return sendAsync(requestBuilder(get(), URL_JOB_BUILD_ARTIFACT, path, jobName, buildNumber, artifactPath));
    }

    @Override
    public CompletableFuture<TestReport> getTestReport(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(get(), URL_JOB_BUILD_TESTS, path, jobName, buildNumber), TestReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<CoverageReport> getTestCoverageReport(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(get(), URL_JOB_BUILD_COVERAGE, path, jobName, buildNumber), CoverageReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int bufferOffset) {
        return sendAsync(requestBuilder(get(), URL_JOB_LAST_BUILD_CONSOLE, path, jobName, bufferOffset))
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int buildNumber, int bufferOffset) {
        return sendAsync(requestBuilder(get(), URL_JOB_BUILD_CONSOLE, path, jobName, buildNumber, bufferOffset))
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Void> createFolder(Path path, String folderName) {
        return sendAsync(requestBuilder(post(), URL_FOLDER_CREATE, path, folderName, FOLDER_MODE), Void.class);
    }

    @Override
    public CompletableFuture<Void> createJob(Path path, String jobName, String configXML) {
        return sendAsync(requestBuilder(post(), URL_JOB_CREATE, path, jobName)
                .setBody(configXML, APPLICATION_XML), Void.class);
    }

    @Override
    public CompletableFuture<Void> createView(Path path, String viewName) {
        return sendAsync(requestBuilder(post(), URL_VIEW_CREATE, path)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .addParameters(ofFormData(Map.of(
                        "name", viewName,
                        "mode", LIST_VIEW,
                        "json", serialize(new ViewForm(viewName, LIST_VIEW))
                ))), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateJobConfiguration(Path path, String jobName, String configXML) {
        return sendAsync(requestBuilder(post(), URL_JOB_CONFIGURATION, path, jobName)
                .setBody(configXML, APPLICATION_XML), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateViewConfiguration(Path path, String viewName, String configXML) {
        return sendAsync(requestBuilder(post(), URL_VIEW_CONFIGURATION, path, viewName)
                .setBody(configXML, APPLICATION_XML), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameFolder(Path path, String folderName, String newName) {
        return sendAsync(requestBuilder(post(), URL_FOLDER_RENAME, path, folderName, newName), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameJob(Path path, String jobName, String newName) {
        return sendAsync(requestBuilder(post(), URL_JOB_RENAME, path, jobName, newName), Void.class);
    }

    @Override
    public CompletableFuture<Void> enableJob(Path path, String jobName) {
        return sendAsync(requestBuilder(post(), URL_JOB_ENABLE, path, jobName), Void.class);
    }

    @Override
    public CompletableFuture<Void> disableJob(Path path, String jobName) {
        return sendAsync(requestBuilder(post(), URL_JOB_DISABLE, path, jobName), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteFolder(Path path, String folderName) {
        return sendAsync(requestBuilder(post(), URL_FOLDER_DELETE, path, folderName), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteJob(Path path, String jobName) {
        return sendAsync(requestBuilder(post(), URL_JOB_DELETE, path, jobName), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteView(Path path, String viewName) {
        return sendAsync(requestBuilder(post(), URL_VIEW_DELETE, path, viewName), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName) {
        return sendAsync(requestBuilder(post(), URL_JOB_BUILD, path, jobName), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName, Map<Object, Object> properties) {
        return sendAsync(requestBuilder(post(), URL_JOB_BUILD_PARAMETERS, path, jobName, queryFromKeyValue(properties)), Void.class);
    }

    @Override
    public CompletableFuture<Void> stopBuild(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(post(), URL_JOB_BUILD_STOP, path, jobName, buildNumber), Void.class);
    }

    @Data
    @AllArgsConstructor
    protected static class ViewForm {

        private String name;
        private String mode;

    }

}
