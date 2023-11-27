package com.chavaillaz.client.jenkins.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder implements List<Job> {

    private String displayName;
    private String fullDisplayName;
    private String fullName;
    @Delegate
    private List<Job> jobs;
    private String url;
    private List<View> views;

}
