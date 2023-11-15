package com.chavaillaz.jenkins.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewInfo extends View {

    private String description;
    private List<Job> jobs;
    private List<Property> property;
    private List<View> views;

}
