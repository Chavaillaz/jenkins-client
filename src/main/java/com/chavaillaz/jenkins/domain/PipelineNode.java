package com.chavaillaz.jenkins.domain;

import static com.chavaillaz.jenkins.utility.Utils.dateTimeFromMs;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipelineNode {

    private Duration duration;
    private String id;
    private String name;
    private List<PipelineStage> stageFlowNodes;
    private OffsetDateTime startTime;
    private PipelineStatus status;

    @JsonSetter
    public void setStartTimeMillis(long ms) {
        this.startTime = dateTimeFromMs(ms);
    }

    @JsonSetter
    public void setDurationMillis(long ms) {
        this.duration = Duration.ofMillis(ms);
    }

}
