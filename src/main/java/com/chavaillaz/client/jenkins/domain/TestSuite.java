package com.chavaillaz.client.jenkins.domain;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import com.chavaillaz.client.jenkins.utility.Utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite {

    private List<TestCase> cases;
    private OffsetDateTime date;
    private Duration duration;
    private List<String> enclosingBlockNames;
    private List<String> enclosingBlocks;
    private String id;
    private String name;
    private String nodeId;
    private String stderr;
    private String stdout;

    @JsonSetter
    public void setDuration(double seconds) {
        this.duration = Duration.ofMillis(Math.round(seconds * 1000));
    }

    @JsonSetter
    public void setTimestamp(Long ms) {
        Optional.ofNullable(ms)
                .map(Utils::dateTimeFromMs)
                .ifPresent(dateFromTimestamp -> this.date = dateFromTimestamp);
    }

}
