package com.chavaillaz.client.jenkins;

import static com.chavaillaz.client.common.AbstractHttpClient.HEADER_COOKIE;
import static com.chavaillaz.client.common.utility.Utils.encodeBase64;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.chavaillaz.client.common.security.AnonymousAuthentication;
import com.chavaillaz.client.jenkins.domain.Crumb;

import lombok.Getter;

/**
 * Implementation of the authentication specificities for Jenkins.
 */
@Getter
public class JenkinsAuthentication extends AnonymousAuthentication {

    private final String username;
    private final String password;
    private Crumb crumb;

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
        addHeader.accept("Authorization", getAuthorizationHeader());

        if (getCrumb() != null) {
            addHeader.accept(HEADER_COOKIE, substringBefore(crumb.getSessionIdCookie(), ";"));
            addHeader.accept(crumb.getCrumbRequestField(), crumb.getCrumb());
        }
    }

    @Override
    public void fillCookies(BiConsumer<String, String> addCookie) {
        super.fillCookies(addCookie);
        if (getCrumb() != null) {
            String session = substringBefore(crumb.getSessionIdCookie(), ";");
            addCookie.accept(substringBefore(session, "="), substringAfter(session, "="));
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
