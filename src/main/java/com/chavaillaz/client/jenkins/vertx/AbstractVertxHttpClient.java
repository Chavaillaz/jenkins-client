package com.chavaillaz.client.jenkins.vertx;

import static com.chavaillaz.client.jenkins.JenkinsClient.JENKINS_COOKIES_JSESSIONID;
import static com.chavaillaz.client.jenkins.JenkinsClient.SET_COOKIE;
import static com.chavaillaz.client.jenkins.api.UserApi.URL_CRUMB;
import static io.vertx.core.http.HttpMethod.GET;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.domain.Crumb;
import com.chavaillaz.client.jenkins.exception.JenkinsResponseException;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public abstract class AbstractVertxHttpClient extends com.chavaillaz.client.common.vertx.AbstractVertxHttpClient {

    protected JenkinsAuthentication authentication;

    /**
     * Creates a new abstract client based on Vert.x HTTP client.
     *
     * @param client         The web client to use
     * @param baseUrl        The base URL of endpoints
     * @param authentication The authentication information
     */
    protected AbstractVertxHttpClient(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
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
        return handleAsyncBase(requestBuilder(GET, URL_CRUMB, getAuthentication().getUsername()).send())
                .thenApply(this::loadCrumb)
                .join();
    }

    /**
     * Generates the {@link Crumb} object based on the response received by the Jenkins crumb issuer.
     *
     * @param httpResponse The HTTP response of the crumb issuer
     * @return The {@link Crumb} object created
     */
    @SneakyThrows
    protected Crumb loadCrumb(HttpResponse<Buffer> httpResponse) {
        Crumb crumb = deserialize(httpResponse.bodyAsString(), Crumb.class);
        httpResponse.headers().getAll(SET_COOKIE).stream()
                .filter(value -> value.startsWith(JENKINS_COOKIES_JSESSIONID))
                .findFirst()
                .ifPresent(crumb::setSessionIdCookie);
        return crumb;
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JenkinsResponseException(code, body);
    }

}
