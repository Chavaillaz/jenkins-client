package com.chavaillaz.client.jenkins.domain.folder;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.ArrayList;
import java.util.List;

import com.chavaillaz.client.jenkins.domain.job.Job;
import com.chavaillaz.client.jenkins.domain.view.View;
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
    private List<Job> jobs = new ArrayList<>();
    private String url;
    private List<View> views = new ArrayList<>();

}
