package com.chavaillaz.client.jenkins.domain.plugin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plugin {

    private List<Plugin> dependencies = new ArrayList<>();
    private Boolean downgradable;
    private Boolean enabled;
    private Boolean hasUpdate;
    private String longName;
    private Boolean pinned;
    private String requiredCoreVersion;
    private String shortName;
    private String supportsDynamicLoad;
    private String url;
    private String version;

}

