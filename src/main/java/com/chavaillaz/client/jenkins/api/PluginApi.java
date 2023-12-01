package com.chavaillaz.client.jenkins.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

import com.chavaillaz.client.jenkins.domain.plugin.Plugins;

public interface PluginApi {

    UnaryOperator<String> XML_PLUGIN_INSTALLATION = id -> "<jenkins><install plugin=\\\"" + id + "\\\"/></jenkins>";
    String URL_PLUGINS = "pluginManager/api/json?depth={0}";
    String URL_PLUGINS_INSTALLATION = "pluginManager/installNecessaryPlugins";

    /**
     * Gets the list of plugins with their dependencies and with all their fields.
     *
     * @return A {@link CompletableFuture} with the plugins
     */
    default CompletableFuture<Plugins> getPlugins() {
        return getPlugins(2);
    }

    /**
     * Gets the list of plugins.
     *
     * @param depth The depth level to return
     * @return A {@link CompletableFuture} with the plugins
     */
    CompletableFuture<Plugins> getPlugins(Integer depth);

    /**
     * Installs a new plugin.
     *
     * @param pluginId The plugin identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> installPlugin(String pluginId);

}
