package com.chavaillaz.client.jenkins.domain;

import static com.chavaillaz.client.jenkins.utility.Utils.dateTimeFromMs;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueItem {

    private boolean blocked;
    private boolean buildable;
    private long buildableStartMilliseconds;
    private boolean cancelled;
    private OffsetDateTime date;
    private int id;
    private OffsetDateTime inQueueSince;
    private boolean stuck;
    private Task task;
    private String url;
    private String why;

    @JsonSetter
    public void setInQueueSince(long ms) {
        this.inQueueSince = dateTimeFromMs(ms);
    }

    @JsonSetter
    public void setTimestamp(long ms) {
        this.date = dateTimeFromMs(ms);
    }

}
