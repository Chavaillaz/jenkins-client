package com.chavaillaz.jenkins.domain;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {

    @JsonIgnore
    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> fields = new HashMap<>();

    @JsonProperty("_class")
    private String type;

    /**
     * Gets the value corresponding to the given property name.
     *
     * @param name The property name
     * @return The corresponding value
     */
    public Object get(String name) {
        return fields.get(name);
    }

}
