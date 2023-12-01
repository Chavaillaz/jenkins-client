package com.chavaillaz.client.jenkins.domain.system;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Load {

    private Map<String, String> availableExecutors;
    private Map<String, String> busyExecutors;
    private Map<String, String> connectingExecutors;
    private Map<String, String> definedExecutors;
    private Map<String, String> idleExecutors;
    private Map<String, String> onlineExecutors;
    private Map<String, String> queueLength;
    private Map<String, String> totalExecutors;
    private Map<String, String> totalQueueLength;

}
