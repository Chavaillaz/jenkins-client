package com.chavaillaz.client.jenkins.okhttp;

import static okhttp3.RequestBody.create;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.domain.plugin.Plugins;
import okhttp3.OkHttpClient;

/**
 * Implementation of {@link PluginApi} for OkHttp.
 */
public class OkHttpPluginApi extends AbstractOkHttpClient implements PluginApi {

    /**
     * Creates a new plugin client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public OkHttpPluginApi(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Plugins> getPlugins(Integer depth) {
        return sendAsync(requestBuilder(URL_PLUGINS, depth).get(), Plugins.class);
    }

    @Override
    public CompletableFuture<Void> installPlugin(String pluginId) {
        return sendAsync(requestBuilder(URL_PLUGINS_INSTALLATION)
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .post(create(XML_PLUGIN_INSTALLATION.apply(pluginId), MEDIA_TYPE_XML)), Void.class);
    }

}
