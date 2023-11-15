package com.chavaillaz.jenkins.domain;

import static com.chavaillaz.jenkins.client.JenkinsConstant.NODE_PATTERN;
import static com.chavaillaz.jenkins.utility.Utils.dateTimeFromMs;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.regex.Matcher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipelineStage {

    private Duration duration;
    private Error error;
    private String id;
    @JsonProperty("_links")
    private Links links;
    private String name;
    private Duration pauseDuration;
    private OffsetDateTime startTime;
    private PipelineStatus status;

    public String getNode() {
        return Optional.ofNullable(getLinks())
                .map(list -> list.getLink("self"))
                .map(NODE_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .orElse(null);
    }

    @JsonSetter
    public void setStartTimeMillis(long ms) {
        this.startTime = dateTimeFromMs(ms);
    }

    @JsonSetter
    public void setDurationMillis(long ms) {
        this.duration = Duration.ofMillis(ms);
    }

    @JsonSetter
    public void setPauseDurationMillis(long ms) {
        this.pauseDuration = Duration.ofMillis(ms);
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error {

        private String message;
        private String stackTrace;
        private String type;

    }

}
