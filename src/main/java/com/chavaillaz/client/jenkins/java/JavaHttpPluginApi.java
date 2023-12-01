package com.chavaillaz.client.jenkins.java;

import static java.net.http.HttpRequest.BodyPublishers.ofString;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.domain.Plugins;

/**
 * Implementation of {@link PluginApi} for Java HTTP.
 */
public class JavaHttpPluginApi extends AbstractJavaHttpClient implements PluginApi {

    /**
     * Creates a new plugin client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpPluginApi(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Plugins> getPlugins(Integer depth) {
        return sendAsync(requestBuilder(URL_PLUGINS, depth).GET(), Plugins.class);
    }

    @Override
    public CompletableFuture<Void> installPlugin(String pluginId) {
        return sendAsync(requestBuilder(URL_PLUGINS_INSTALLATION)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .POST(ofString(XML_PLUGIN_INSTALLATION.apply(pluginId))), Void.class);
    }

}
