package com.chavaillaz.client.jenkins.okhttp;

import static com.chavaillaz.client.common.utility.Utils.queryFromKeyValue;
import static com.chavaillaz.client.jenkins.JenkinsConstant.FOLDER_MODE;
import static com.chavaillaz.client.jenkins.JenkinsConstant.LIST_VIEW;
import static okhttp3.RequestBody.create;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.math.NumberUtils;

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
import okhttp3.OkHttpClient;

/**
 * Implementation of {@link JobApi} for OkHttp.
 */
public class OkHttpJobApi extends AbstractOkHttpClient implements JobApi {

    /**
     * Creates a new job client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public OkHttpJobApi(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Folder> getFolder(Path path) {
        return sendAsync(requestBuilder(URL_FOLDER, path).get(), Folder.class);
    }

    @Override
    public CompletableFuture<ViewInfo> getView(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_INFO, path, viewName).get(), ViewInfo.class);
    }

    @Override
    public CompletableFuture<JobInfo> getJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_INFO, path, jobName).get(), JobInfo.class);
    }

    @Override
    public CompletableFuture<String> getFolderConfiguration(Path path) {
        return sendAsync(requestBuilder(URL_FOLDER_CONFIGURATION, path).get())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobConfiguration(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_CONFIGURATION, path, jobName).get())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getViewConfiguration(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_CONFIGURATION, path, viewName).get())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobDescription(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_DESCRIPTION, path, jobName).get())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Integer> getLastBuildNumber(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_LAST_BUILD_NUMBER, path, jobName).get())
                .thenApply(Utils::readInputStream)
                .thenApply(NumberUtils::toInt)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<BuildInfo> getBuild(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_INFO, path, jobName, buildNumber).get(), BuildInfo.class);
    }

    @Override
    public CompletableFuture<InputStream> getArtifact(Path path, String jobName, int buildNumber, String artifactPath) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_ARTIFACT, path, jobName, buildNumber, artifactPath).get());
    }

    @Override
    public CompletableFuture<TestReport> getTestReport(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_TESTS, path, jobName, buildNumber).get(), TestReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<CoverageReport> getTestCoverageReport(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_COVERAGE, path, jobName, buildNumber).get(), CoverageReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int bufferOffset) {
        return sendAsync(requestBuilder(URL_JOB_LAST_BUILD_CONSOLE, path, jobName, bufferOffset).get())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int buildNumber, int bufferOffset) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_CONSOLE, path, jobName, buildNumber, bufferOffset).get())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Void> createFolder(Path path, String folderName) {
        return sendAsync(requestBuilder(URL_FOLDER_CREATE, path, folderName, FOLDER_MODE).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> createJob(Path path, String jobName, String configXML) {
        return sendAsync(requestBuilder(URL_JOB_CREATE, path, jobName).post(create(configXML, MEDIA_TYPE_XML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> createView(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_CREATE, path)
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .post(ofFormData(Map.of(
                        "name", viewName,
                        "mode", LIST_VIEW,
                        "json", serialize(new ViewForm(viewName, LIST_VIEW))
                ))), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateJobConfiguration(Path path, String jobName, String configXML) {
        return sendAsync(requestBuilder(URL_JOB_CONFIGURATION, path, jobName).post(create(configXML, MEDIA_TYPE_XML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateViewConfiguration(Path path, String viewName, String configXML) {
        return sendAsync(requestBuilder(URL_VIEW_CONFIGURATION, path, viewName).post(create(configXML, MEDIA_TYPE_XML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameFolder(Path path, String folderName, String newName) {
        return sendAsync(requestBuilder(URL_FOLDER_RENAME, path, folderName, newName).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameJob(Path path, String jobName, String newName) {
        return sendAsync(requestBuilder(URL_JOB_RENAME, path, jobName, newName).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> enableJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_ENABLE, path, jobName).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> disableJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_DISABLE, path, jobName).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteFolder(Path path, String folderName) {
        return sendAsync(requestBuilder(URL_FOLDER_DELETE, path, folderName)
                .post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_DELETE, path, jobName).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteView(Path path, String viewName) {
        return sendAsync(requestBuilder(URL_VIEW_DELETE, path, viewName).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName) {
        return sendAsync(requestBuilder(URL_JOB_BUILD, path, jobName).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName, Map<Object, Object> properties) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_PARAMETERS, path, jobName, queryFromKeyValue(properties)).post(EMPTY_BODY), Void.class);
    }

    @Override
    public CompletableFuture<Void> stopBuild(Path path, String jobName, int buildNumber) {
        return sendAsync(requestBuilder(URL_JOB_BUILD_STOP, path, jobName, buildNumber).post(EMPTY_BODY), Void.class);
    }

    @Data
    @AllArgsConstructor
    protected static class ViewForm {

        private String name;
        private String mode;

    }

}
