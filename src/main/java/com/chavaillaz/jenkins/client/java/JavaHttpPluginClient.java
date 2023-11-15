package com.chavaillaz.jenkins.client.java;

import static java.net.http.HttpRequest.BodyPublishers.ofString;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jenkins.client.Authentication;
import com.chavaillaz.jenkins.client.PluginClient;
import com.chavaillaz.jenkins.domain.Plugins;

public class JavaHttpPluginClient extends AbstractJavaHttpClient implements PluginClient {

    /**
     * Creates a new plugin client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public JavaHttpPluginClient(HttpClient client, String baseUrl, Authentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Plugins> getPlugins(Integer depth, String tree) {
        return sendAsync(requestBuilder(URL_PLUGINS, depth, tree).GET(), Plugins.class);
    }

    @Override
    public CompletableFuture<Void> installPlugin(String pluginId) {
        return sendAsync(requestBuilder(URL_PLUGINS_INSTALLATION)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .POST(ofString("<jenkins><install plugin=\\\"" + pluginId + "\\\"/></jenkins>")), Void.class);
    }

}
