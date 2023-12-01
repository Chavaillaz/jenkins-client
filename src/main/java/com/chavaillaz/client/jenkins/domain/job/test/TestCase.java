package com.chavaillaz.client.jenkins.domain.job.test;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase {

    private int age;
    private String className;
    private Duration duration;
    private String errorDetails;
    private String errorStackTrace;
    private int failedSince;
    private String name;
    private boolean skipped;
    private String skippedMessage;
    private String status;
    private String stderr;
    private String stdout;

    @JsonSetter
    public void setDuration(double seconds) {
        this.duration = Duration.ofMillis(Math.round(seconds * 1000));
    }

}
