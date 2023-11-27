package com.chavaillaz.client.jenkins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipelineArtifact {

    private String id;
    private String name;
    private String path;
    private Long size;
    private String url;

}
