package com.chavaillaz.jenkins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Culprit {

    private String absoluteUrl;
    private String fullName;

}
