package com.chavaillaz.client.jenkins.domain.queue;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueItems implements List<QueueItem> {

    @Delegate
    private List<QueueItem> items;

}
