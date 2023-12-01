package com.chavaillaz.client.jenkins.domain.job.pipeline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    private String message;
    private String stackTrace;
    private String type;

}
