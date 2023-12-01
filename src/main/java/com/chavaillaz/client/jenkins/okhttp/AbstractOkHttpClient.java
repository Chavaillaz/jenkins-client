package com.chavaillaz.client.jenkins.okhttp;

import static com.chavaillaz.client.common.okhttp.OkHttpUtils.getBody;
import static com.chavaillaz.client.jenkins.JenkinsClient.JENKINS_COOKIES_JSESSIONID;
import static com.chavaillaz.client.jenkins.JenkinsClient.SET_COOKIE;
import static com.chavaillaz.client.jenkins.api.UserApi.URL_CRUMB;

import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.domain.Crumb;
import com.chavaillaz.client.jenkins.exception.JenkinsResponseException;
import lombok.Getter;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@Getter
public abstract class AbstractOkHttpClient extends com.chavaillaz.client.common.okhttp.AbstractOkHttpClient {

    protected JenkinsAuthentication authentication;

    /**
     * Creates a new abstract client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The base URL of service API
     * @param authentication The authentication information
     */
    protected AbstractOkHttpClient(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
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
        return sendAsyncBase(requestBuilder(URL_CRUMB, getAuthentication().getUsername()).get())
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
    protected Crumb loadCrumb(Response httpResponse) {
        Crumb crumb = deserialize(getBody(httpResponse), Crumb.class);
        httpResponse.headers().toMultimap().get(SET_COOKIE).stream()
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
