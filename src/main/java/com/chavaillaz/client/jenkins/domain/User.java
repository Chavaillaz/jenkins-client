package com.chavaillaz.client.jenkins.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String absoluteUrl;
    private String description;
    private String fullName;
    private String id;
    private List<Property> property;

}
