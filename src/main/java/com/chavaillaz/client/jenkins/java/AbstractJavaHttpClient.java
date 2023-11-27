package com.chavaillaz.client.jenkins.java;

import static com.chavaillaz.client.jenkins.JenkinsClient.JENKINS_COOKIES_JSESSIONID;
import static com.chavaillaz.client.jenkins.JenkinsClient.SET_COOKIE;
import static com.chavaillaz.client.jenkins.api.UserClient.URL_CRUMB;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.domain.Crumb;
import com.chavaillaz.client.jenkins.exception.JenkinsResponseException;
import lombok.Getter;

/**
 * Abstract class implementing common parts to call the Jenkins REST API for Java HTTP.
 */
@Getter
public class AbstractJavaHttpClient extends com.chavaillaz.client.common.java.AbstractJavaHttpClient {

    protected JenkinsAuthentication authentication;

    /**
     * Creates a new abstract client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    public AbstractJavaHttpClient(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
        this.authentication = authentication;
        // Load in advance the crumb for all requests, even if not necessary
        this.authentication.loadCrumbIfAbsent(this::loadCrumb);
    }

    /**
     * Generates the {@link Crumb} object by calling the Jenkins crumb issuer.
     *
     * @return The {@link Crumb} object created
     */
    protected Crumb loadCrumb() {
        HttpRequest.Builder request = requestBuilder(URL_CRUMB, getAuthentication().getUsername()).GET();
        return client.sendAsync(request.build(), BodyHandlers.ofString())
                .thenApply(this::checkResponse)
                .thenApply(this::loadCrumb)
                .join();
    }

    /**
     * Generates the {@link Crumb} object based on the response received by the Jenkins crumb issuer.
     *
     * @param httpResponse The HTTP response of the crumb issuer
     * @return The {@link Crumb} object created
     */
    protected Crumb loadCrumb(HttpResponse<String> httpResponse) {
        Crumb crumb = deserialize(httpResponse.body(), objectMapper.constructType(Crumb.class));
        httpResponse.headers().allValues(SET_COOKIE).stream()
                .filter(headerValue -> headerValue.startsWith(JENKINS_COOKIES_JSESSIONID))
                .findFirst()
                .ifPresent(crumb::setSessionIdCookie);
        return crumb;
    }

    @Override
    protected HttpRequest.Builder requestBuilder(String url, Object... parameters) {
        HttpRequest.Builder builder = super.requestBuilder(url, parameters);

        if (getAuthentication().getCrumb() != null) {
            Crumb crumb = getAuthentication().getCrumb();
            builder.header(crumb.getCrumbRequestField(), crumb.getCrumb())
                    .header(HEADER_COOKIE, crumb.getSessionIdCookie());
        }

        return builder;
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JenkinsResponseException(code, body);
    }

}
