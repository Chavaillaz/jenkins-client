package com.chavaillaz.client.jenkins.domain.queue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    private String name;
    private String url;

}
