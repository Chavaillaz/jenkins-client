package com.chavaillaz.jenkins.client;

import java.util.Base64;
import java.util.function.Supplier;

import com.chavaillaz.client.Authentication;
import com.chavaillaz.jenkins.domain.Crumb;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Implementation of the authentication specificities for Jenkins.
 */
@Getter
@NoArgsConstructor
public class JenkinsAuthentication extends Authentication {

    private Crumb crumb;

    /**
     * Creates a new authentication configuration.
     *
     * @param type     The type of authentication
     * @param username The username or {@code null} for anonymous access
     * @param password The password, token or {@code null} for anonymous access
     */
    public JenkinsAuthentication(AuthenticationType type, String username, String password) {
        super(type, username, password);
    }

    /**
     * Encodes a {@link String} to base 64 format.
     *
     * @param value The value to encode
     * @return The encoded value
     */
    private static String encodeBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    /**
     * Gets the HTTP Header {@code Authorization} to transmit for authentication.
     *
     * @return The authorization header
     */
    public String getAuthorizationHeader() {
        return switch (getType()) {
            case ANONYMOUS -> encodeBase64("anonymous:");
            case PASSWORD, TOKEN -> "Basic " + encodeBase64(getUsername() + ":" + getPassword());
        };
    }

    public Crumb loadCrumbIfAbsent(Supplier<Crumb> supplier) {
        if (this.crumb == null) {
            this.crumb = supplier.get();
        }
        return this.crumb;
    }

}
