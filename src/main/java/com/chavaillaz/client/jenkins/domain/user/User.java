package com.chavaillaz.client.jenkins.domain.user;

import java.util.ArrayList;
import java.util.List;

import com.chavaillaz.client.jenkins.domain.common.Property;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String absoluteUrl;
    private String description;
    private String fullName;
    private String id;
    private List<Property> property = new ArrayList<>();

}
