package com.chavaillaz.client.jenkins.domain.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {

    private String color;
    private String name;
    @JsonProperty("_class")
    private String type;
    private String url;

    public boolean isFolder() {
        return this.type.contains("Folder");
    }

    public boolean isPipeline() {
        return this.type.contains("WorkflowMultiBranchProject");
    }

    public boolean isJob() {
        return this.type.contains("WorkflowJob");
    }

    public boolean isMaven() {
        return this.type.contains("MavenModuleSet");
    }

}
