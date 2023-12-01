package com.chavaillaz.client.jenkins.domain.job.test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestReport {

    private Duration duration;
    private boolean empty;
    private int failCount;
    private int passCount;
    private int skipCount;
    private List<TestSuite> suites = new ArrayList<>();

    @JsonSetter
    public void setDuration(double seconds) {
        this.duration = Duration.ofMillis(Math.round(seconds * 1000));
    }

}
