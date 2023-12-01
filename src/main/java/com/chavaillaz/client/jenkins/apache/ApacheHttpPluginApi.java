package com.chavaillaz.client.jenkins.apache;

import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.PluginApi;
import com.chavaillaz.client.jenkins.domain.Plugins;

/**
 * Implementation of {@link PluginApi} for Apache HTTP.
 */
public class ApacheHttpPluginApi extends AbstractApacheHttpClient implements PluginApi {

    /**
     * Creates a new plugin client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public ApacheHttpPluginApi(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<Plugins> getPlugins(Integer depth) {
        return sendAsync(requestBuilder(get(), URL_PLUGINS, depth), Plugins.class);
    }

    @Override
    public CompletableFuture<Void> installPlugin(String pluginId) {
        return sendAsync(requestBuilder(post(), URL_PLUGINS_INSTALLATION)
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_XML)
                .setBody(XML_PLUGIN_INSTALLATION.apply(pluginId), APPLICATION_JSON), Void.class);
    }

}
