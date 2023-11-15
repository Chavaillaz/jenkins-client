package com.chavaillaz.jenkins.domain;

import static com.chavaillaz.jenkins.utility.Utils.dateTimeFromMs;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildInfo extends Build {

    private List<Artifact> artifacts;
    private boolean building;
    private String builtOn;
    private List<Culprit> culprits;
    private OffsetDateTime date;
    private String description;
    private String displayName;
    private Duration duration;
    private Duration estimatedDuration;
    private String fullDisplayName;
    private String id;
    private boolean keepLog;
    private String result;

    @JsonSetter
    public void setTimestamp(long ms) {
        this.date = dateTimeFromMs(ms);
    }

    @JsonSetter
    public void setDuration(long ms) {
        this.duration = Duration.ofMillis(ms);
    }

    @JsonSetter
    public void setEstimatedDuration(long ms) {
        this.estimatedDuration = Duration.ofMillis(ms);
    }

}
