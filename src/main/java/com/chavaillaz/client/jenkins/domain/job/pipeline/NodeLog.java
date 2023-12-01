package com.chavaillaz.client.jenkins.domain.job.pipeline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeLog {

    private String consoleUrl;
    private boolean hasMore;
    private Long length;
    private String nodeId;
    private String nodeStatus;
    private String text;

}
