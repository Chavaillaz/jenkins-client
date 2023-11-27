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
public class TestReport {

    private Duration duration;
    private boolean empty;
    private int failCount;
    private int passCount;
    private int skipCount;
    private List<TestSuite> suites;

    @JsonSetter
    public void setDuration(double seconds) {
        this.duration = Duration.ofMillis(Math.round(seconds * 1000));
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestSuite {

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

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestCase {

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

}
