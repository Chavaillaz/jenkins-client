package com.chavaillaz.client.jenkins.domain.job.pipeline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {

    private String abortUrl;
    private String id;
    private String message;
    private String proceedUrl;

}