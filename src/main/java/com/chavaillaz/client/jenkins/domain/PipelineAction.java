package com.chavaillaz.client.jenkins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipelineAction {

    private String abortUrl;
    private String id;
    private String message;
    private String proceedUrl;

}