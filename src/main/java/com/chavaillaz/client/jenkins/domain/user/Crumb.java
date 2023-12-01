package com.chavaillaz.client.jenkins.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Crumb {

    private String crumb;
    private String crumbRequestField;
    private String sessionIdCookie;

}
