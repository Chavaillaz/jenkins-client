package com.chavaillaz.client.jenkins.api;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.domain.Plugins;

public interface PluginApi {

    String URL_PLUGINS = "pluginManager/api/json?depth={0}&tree={1}";
    String URL_PLUGINS_INSTALLATION = "pluginManager/installNecessaryPlugins";

    /**
     * Gets the list of plugins with their dependencies and with all their fields.
     *
     * @return A {@link CompletableFuture} with the plugins
     */
    default CompletableFuture<Plugins> getPlugins() {
        return getPlugins(2, deleteWhitespace("""
                plugins[
                    dependencies[shortName,version],
                    downgradable,
                    enabled,
                    hasUpdate,
                    longName,
                    pinned,
                    requiredCoreVersion,
                    shortName,
                    supportsDynamicLoad,
                    url,
                    version
                ]
                """));
    }

    /**
     * Gets the list of plugins.
     *
     * @param depth The depth level to return
     * @param tree  The list of fields to return
     * @return A {@link CompletableFuture} with the plugins
     */
    CompletableFuture<Plugins> getPlugins(Integer depth, String tree);

    /**
     * Installs a new plugin.
     *
     * @param pluginId The plugin identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> installPlugin(String pluginId);

}
