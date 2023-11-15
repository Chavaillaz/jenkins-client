package com.chavaillaz.jenkins.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobInfo extends Job {

    private boolean buildable;
    private List<Build> builds;
    private boolean concurrentBuild;
    private String description;
    private String displayName;
    private Build firstBuild;
    private boolean inQueue;
    private boolean keepDependencies;
    private Build lastBuild;
    private Build lastCompleteBuild;
    private Build lastFailedBuild;
    private Build lastStableBuild;
    private Build lastSuccessfulBuild;
    private Build lastUnstableBuild;
    private Build lastUnsuccessfulBuild;
    private Integer nextBuildNumber;
    private QueueItem queueItem;

}
