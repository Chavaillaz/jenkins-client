package com.chavaillaz.client.jenkins.domain.job.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Build {

    private Integer number;
    private Long queueId;
    private String url;

}
