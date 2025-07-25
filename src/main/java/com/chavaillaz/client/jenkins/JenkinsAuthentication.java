package com.chavaillaz.client.jenkins;

import static com.chavaillaz.client.common.AbstractHttpClient.HEADER_AUTHORIZATION;
import static com.chavaillaz.client.common.AbstractHttpClient.HEADER_COOKIE;
import static com.chavaillaz.client.common.utility.Utils.encodeBase64;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.chavaillaz.client.common.security.AnonymousAuthentication;
import com.chavaillaz.client.jenkins.domain.user.Crumb;
import lombok.Getter;

/**
 * Implementation of the authentication specificities for Jenkins.
 */
@Getter
public class JenkinsAuthentication extends AnonymousAuthentication {

    protected final String username;
    protected final String password;
    protected Crumb crumb;

    /**
     * Creates a new authentication configuration.
     *
     * @param username The username or {@code null} for anonymous access
     * @param password The password, token or {@code null} for anonymous access
     */
    public JenkinsAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void fillHeaders(BiConsumer<String, String> addHeader) {
        addHeader.accept(HEADER_AUTHORIZATION, getAuthorizationHeader());

        if (crumb != null) {
            addHeader.accept(HEADER_COOKIE, substringBefore(crumb.getSessionIdCookie(), ";"));
            addHeader.accept(crumb.getCrumbRequestField(), crumb.getCrumb());
        }
    }

    /**
     * Gets the HTTP Header {@code Authorization} to transmit for authentication.
     *
     * @return The authorization header
     */
    public String getAuthorizationHeader() {
        if (isBlank(username) || isBlank(password)) {
            return encodeBase64("anonymous:");
        } else {
            return "Basic " + encodeBase64(getUsername() + ":" + getPassword());
        }
    }

    /**
     * Gets the crumb and loads it if not already retrieved
     *
     * @param supplier The function to get the crumb
     * @return The crumb stored or loaded
     */
    public Crumb loadCrumbIfAbsent(Supplier<Crumb> supplier) {
        if (this.crumb == null) {
            this.crumb = supplier.get();
        }
        return this.crumb;
    }

}
