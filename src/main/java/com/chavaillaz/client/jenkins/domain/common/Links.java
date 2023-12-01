package com.chavaillaz.client.jenkins.domain.common;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonIgnore
    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Map<String, String>> fields = new HashMap<>();

    /**
     * Gets the link value (URL) of the given link name.
     *
     * @param name The link name
     * @return The corresponding URL
     */
    public String getLink(String name) {
        return fields.get(name).get("href");
    }

}
