package com.chavaillaz.client.jenkins.domain.job.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artifact {

    private String displayPath;
    private String fileName;
    private String relativePath;

}