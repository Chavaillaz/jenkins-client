package com.chavaillaz.client.jenkins.domain.job.pipeline;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.chavaillaz.client.jenkins.domain.common.Links;
import com.chavaillaz.client.jenkins.utility.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Run {

    private Duration duration;
    private OffsetDateTime endTime;
    private String id;
    @JsonProperty("_links")
    private Links links;
    private String name;
    private Duration pauseDuration;
    private Duration queueDuration;
    private List<Stage> stages = new ArrayList<>();
    private OffsetDateTime startTime;
    private Status status;

    @JsonSetter
    public void setStartTimeMillis(long ms) {
        this.startTime = Utils.dateTimeFromMs(ms);
    }

    @JsonSetter
    public void setEndTimeMillis(long ms) {
        this.endTime = Utils.dateTimeFromMs(ms);
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
