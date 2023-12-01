package com.chavaillaz.client.jenkins.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

    private TokenData data;
    private String status;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TokenData {

        private String tokenName;
        private String tokenUuid;
        private String tokenValue;

    }

}
