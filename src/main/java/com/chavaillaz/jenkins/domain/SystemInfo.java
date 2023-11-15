package com.chavaillaz.jenkins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemInfo {

    private String hudsonVersion;
    private String instanceIdentity;
    private String jenkinsSession;
    private String jenkinsVersion;
    private String server;
    private String sshEndpoint;

}
