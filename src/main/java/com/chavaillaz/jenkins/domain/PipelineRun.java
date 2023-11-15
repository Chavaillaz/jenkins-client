package com.chavaillaz.jenkins.domain;

import static com.chavaillaz.jenkins.utility.Utils.dateTimeFromMs;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipelineRun {

    private Duration duration;
    private OffsetDateTime endTime;
    private String id;
    @JsonProperty("_links")
    private Links links;
    private String name;
    private Duration pauseDuration;
    private Duration queueDuration;
    private List<PipelineStage> stages;
    private OffsetDateTime startTime;
    private PipelineStatus status;

    @JsonSetter
    public void setStartTimeMillis(long ms) {
        this.startTime = dateTimeFromMs(ms);
    }

    @JsonSetter
    public void setEndTimeMillis(long ms) {
        this.endTime = dateTimeFromMs(ms);
    }

    @JsonSetter
    public void setDurationMillis(long ms) {
        this.duration = Duration.ofMillis(ms);
    }

    @JsonSetter
    public void setQueueDurationMillis(long ms) {
        this.queueDuration = Duration.ofMillis(ms);
    }

    @JsonSetter
    public void setPauseDurationMillis(long ms) {
        this.pauseDuration = Duration.ofMillis(ms);
    }

}
