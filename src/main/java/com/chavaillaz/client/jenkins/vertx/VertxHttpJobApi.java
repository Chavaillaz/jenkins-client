package com.chavaillaz.client.jenkins.vertx;

import static com.chavaillaz.client.common.utility.Utils.encodeQuery;
import static com.chavaillaz.client.common.vertx.VertxUtils.formData;
import static com.chavaillaz.client.jenkins.JenkinsConstant.FOLDER_MODE;
import static com.chavaillaz.client.jenkins.JenkinsConstant.LIST_VIEW;
import static io.vertx.core.buffer.Buffer.buffer;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;

import java.io.InputStream;
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
import io.vertx.ext.web.client.WebClient;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Implementation of {@link JobApi} for Vert.x HTTP.
 */
public class VertxHttpJobApi extends AbstractVertxHttpClient implements JobApi {

    /**
     * Creates a new job client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public VertxHttpJobApi(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Folder> getFolder(Path path) {
        return handleAsync(requestBuilder(GET, URL_FOLDER, path).send(), Folder.class);
    }

    @Override
    public CompletableFuture<ViewInfo> getView(Path path, String viewName) {
        return handleAsync(requestBuilder(GET, URL_VIEW_INFO, path, viewName).send(), ViewInfo.class);
    }

    @Override
    public CompletableFuture<JobInfo> getJob(Path path, String jobName) {
        return handleAsync(requestBuilder(GET, URL_JOB_INFO, path, jobName).send(), JobInfo.class);
    }

    @Override
    public CompletableFuture<String> getFolderConfiguration(Path path) {
        return handleAsync(requestBuilder(GET, URL_FOLDER_CONFIGURATION, path).send())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobConfiguration(Path path, String jobName) {
        return handleAsync(requestBuilder(GET, URL_JOB_CONFIGURATION, path, jobName).send())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getViewConfiguration(Path path, String viewName) {
        return handleAsync(requestBuilder(GET, URL_VIEW_CONFIGURATION, path, viewName).send())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getJobDescription(Path path, String jobName) {
        return handleAsync(requestBuilder(GET, URL_JOB_DESCRIPTION, path, jobName).send())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Integer> getLastBuildNumber(Path path, String jobName) {
        return handleAsync(requestBuilder(GET, URL_JOB_LAST_BUILD_NUMBER, path, jobName).send())
                .thenApply(Utils::readInputStream)
                .thenApply(NumberUtils::toInt)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<BuildInfo> getBuild(Path path, String jobName, int buildNumber) {
        return handleAsync(requestBuilder(GET, URL_JOB_BUILD_INFO, path, jobName, buildNumber).send(), BuildInfo.class);
    }

    @Override
    public CompletableFuture<InputStream> getArtifact(Path path, String jobName, int buildNumber, String artifactPath) {
        return handleAsync(requestBuilder(GET, URL_JOB_BUILD_ARTIFACT, path, jobName, buildNumber, artifactPath).send());
    }

    @Override
    public CompletableFuture<TestReport> getTestReport(Path path, String jobName, int buildNumber) {
        return handleAsync(requestBuilder(GET, URL_JOB_BUILD_TESTS, path, jobName, buildNumber).send(), TestReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<CoverageReport> getTestCoverageReport(Path path, String jobName, int buildNumber) {
        return handleAsync(requestBuilder(GET, URL_JOB_BUILD_COVERAGE, path, jobName, buildNumber).send(), CoverageReport.class)
                .exceptionally(exception -> null);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int bufferOffset) {
        return handleAsync(requestBuilder(GET, URL_JOB_LAST_BUILD_CONSOLE, path, jobName, bufferOffset).send())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<String> getConsoleOutput(Path path, String jobName, int buildNumber, int bufferOffset) {
        return handleAsync(requestBuilder(GET, URL_JOB_BUILD_CONSOLE, path, jobName, buildNumber, bufferOffset).send())
                .thenApply(Utils::readInputStream);
    }

    @Override
    public CompletableFuture<Void> createFolder(Path path, String folderName) {
        return handleAsync(requestBuilder(POST, URL_FOLDER_CREATE, path, folderName, FOLDER_MODE).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> createJob(Path path, String jobName, String configXML) {
        return handleAsync(requestBuilder(POST, URL_JOB_CREATE, path, jobName)
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .sendBuffer(buffer(configXML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> createView(Path path, String viewName) {
        return handleAsync(requestBuilder(POST, URL_VIEW_CREATE, path)
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .sendForm(formData(Map.of(
                        "name", viewName,
                        "mode", LIST_VIEW,
                        "json", serialize(new ViewCreation(viewName, LIST_VIEW))
                ))), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateJobConfiguration(Path path, String jobName, String configXML) {
        return handleAsync(requestBuilder(POST, URL_JOB_CONFIGURATION, path, jobName)
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .sendBuffer(buffer(configXML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> updateViewConfiguration(Path path, String viewName, String configXML) {
        return handleAsync(requestBuilder(POST, URL_VIEW_CONFIGURATION, path, viewName)
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .sendBuffer(buffer(configXML)), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameFolder(Path path, String folderName, String newName) {
        return handleAsync(requestBuilder(POST, URL_FOLDER_RENAME, path, folderName, newName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> renameJob(Path path, String jobName, String newName) {
        return handleAsync(requestBuilder(POST, URL_JOB_RENAME, path, jobName, newName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> enableJob(Path path, String jobName) {
        return handleAsync(requestBuilder(POST, URL_JOB_ENABLE, path, jobName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> disableJob(Path path, String jobName) {
        return handleAsync(requestBuilder(POST, URL_JOB_DISABLE, path, jobName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteFolder(Path path, String folderName) {
        return handleAsync(requestBuilder(POST, URL_FOLDER_DELETE, path, folderName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteJob(Path path, String jobName) {
        return handleAsync(requestBuilder(POST, URL_JOB_DELETE, path, jobName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> deleteView(Path path, String viewName) {
        return handleAsync(requestBuilder(POST, URL_VIEW_DELETE, path, viewName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName) {
        return handleAsync(requestBuilder(POST, URL_JOB_BUILD, path, jobName).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> buildJob(Path path, String jobName, Map<Object, Object> properties) {
        return handleAsync(requestBuilder(POST, URL_JOB_BUILD_PARAMETERS, path, jobName, encodeQuery(properties)).send(), Void.class);
    }

    @Override
    public CompletableFuture<Void> stopBuild(Path path, String jobName, int buildNumber) {
        return handleAsync(requestBuilder(POST, URL_JOB_BUILD_STOP, path, jobName, buildNumber).send(), Void.class);
    }

}
