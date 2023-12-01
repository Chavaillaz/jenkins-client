package com.chavaillaz.client.jenkins.vertx;

import static io.vertx.core.buffer.Buffer.buffer;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.domain.Plugins;

import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link PluginApi} for Vert.x HTTP.
 */
public class VertxHttpPluginApi extends AbstractVertxHttpClient implements PluginApi {

    /**
     * Creates a new plugin client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public VertxHttpPluginApi(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Plugins> getPlugins(Integer depth) {
        return handleAsync(requestBuilder(GET, URL_PLUGINS, depth).send(), Plugins.class);
    }

    @Override
    public CompletableFuture<Void> installPlugin(String pluginId) {
        return handleAsync(requestBuilder(POST, URL_PLUGINS_INSTALLATION)
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .sendBuffer(buffer(XML_PLUGIN_INSTALLATION.apply(pluginId))), Void.class);
    }

}
