package com.chavaillaz.client.jenkins.domain.view;

import java.util.ArrayList;
import java.util.List;

import com.chavaillaz.client.jenkins.domain.common.Property;
import com.chavaillaz.client.jenkins.domain.job.Job;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewInfo extends View {

    private String description;
    private List<Job> jobs = new ArrayList<>();
    private List<Property> property = new ArrayList<>();
    private List<View> views = new ArrayList<>();

}
