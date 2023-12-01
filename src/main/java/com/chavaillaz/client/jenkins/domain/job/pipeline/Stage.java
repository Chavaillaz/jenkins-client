package com.chavaillaz.client.jenkins.domain.job.pipeline;

import static com.chavaillaz.client.jenkins.JenkinsConstant.NODE_PATTERN;
import static com.chavaillaz.client.jenkins.utility.Utils.dateTimeFromMs;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.regex.Matcher;

import com.chavaillaz.client.jenkins.domain.common.Links;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stage {

    private Duration duration;
    private Error error;
    private String id;
    @JsonProperty("_links")
    private Links links;
    private String name;
    private Duration pauseDuration;
    private OffsetDateTime startTime;
    private Status status;

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

}
