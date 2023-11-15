package com.chavaillaz.jenkins.client;

import static com.chavaillaz.jenkins.utility.Utils.encodeBase64;

import java.util.function.Supplier;

import com.chavaillaz.jenkins.domain.Crumb;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Authentication {

    private final AuthenticationType type;
    private String username;
    private String password;
    private Crumb crumb;

    /**
     * Creates a new authentication configuration.
     *
     * @param type     The type of authentication
     * @param username The username or {@code null} for anonymous access
     * @param password The password, token or {@code null} for anonymous access
     */
    public Authentication(AuthenticationType type, String username, String password) {
        this(type);
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the HTTP Header {@code Authorization} to transmit for authentication.
     *
     * @return The authorization header
     */
    public String getAuthorizationHeader() {
        return switch (type) {
            case ANONYMOUS -> encodeBase64("anonymous:");
            case PASSWORD, TOKEN -> "Basic " + encodeBase64(username + ":" + password);
        };
    }

    public Crumb loadCrumbIfAbsent(Supplier<Crumb> supplier) {
        if (this.crumb == null) {
            this.crumb = supplier.get();
        }
        return this.crumb;
    }

    public enum AuthenticationType {
        ANONYMOUS,
        PASSWORD,
        TOKEN
    }

}
