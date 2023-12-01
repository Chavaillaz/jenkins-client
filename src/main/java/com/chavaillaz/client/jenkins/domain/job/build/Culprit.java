package com.chavaillaz.client.jenkins.domain.job.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Culprit {

    private String absoluteUrl;
    private String fullName;

}
