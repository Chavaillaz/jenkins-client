package com.chavaillaz.client.jenkins.apache;

import static com.chavaillaz.client.jenkins.JenkinsClient.JENKINS_COOKIES_JSESSIONID;
import static com.chavaillaz.client.jenkins.JenkinsClient.SET_COOKIE;
import static com.chavaillaz.client.jenkins.api.UserApi.URL_CRUMB;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.common.apache.CompletableFutureCallback;
import com.chavaillaz.client.common.exception.ResponseException;
import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.domain.Crumb;
import com.chavaillaz.client.jenkins.exception.JenkinsResponseException;
import lombok.Getter;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Abstract class implementing common parts to call the Jenkins REST API for Apache HTTP.
 */
@Getter
public abstract class AbstractApacheHttpClient extends com.chavaillaz.client.common.apache.AbstractApacheHttpClient {

    protected JenkinsAuthentication authentication;

    /**
     * Creates a new abstract client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication method
     */
    protected AbstractApacheHttpClient(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
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
        return sendAsyncBase(requestBuilder(get(), URL_CRUMB, getAuthentication().getUsername()))
                .thenApply(this::loadCrumb)
                .join();
    }

    /**
     * Generates the {@link Crumb} object based on the response received by the Jenkins crumb issuer.
     *
     * @param httpResponse The HTTP response of the crumb issuer
     * @return The {@link Crumb} object created
     */
    protected Crumb loadCrumb(SimpleHttpResponse httpResponse) {
        Crumb crumb = deserialize(httpResponse.getBodyText(), Crumb.class);
        Arrays.stream(httpResponse.getHeaders(SET_COOKIE))
                .map(NameValuePair::getValue)
                .filter(value -> value.startsWith(JENKINS_COOKIES_JSESSIONID))
                .findFirst()
                .ifPresent(crumb::setSessionIdCookie);
        return crumb;
    }

    @Override
    protected CompletableFuture<SimpleHttpResponse> sendAsyncBase(SimpleRequestBuilder requestBuilder) {
        SimpleHttpRequest request = requestBuilder.build();
        CompletableFuture<SimpleHttpResponse> completableFuture = new CompletableFuture<>();
        client.execute(request, new CompletableFutureCallback(this, request, completableFuture));
        return completableFuture;
    }

    public NameValuePair[] ofFormData(Map<Object, Object> map) {
        List<NameValuePair> parameters = new ArrayList<>();
        map.forEach((key, value) -> parameters.add(new BasicNameValuePair(key.toString(), value.toString())));
        return parameters.toArray(new NameValuePair[0]);
    }

    @Override
    public ResponseException responseException(int code, String body) {
        return new JenkinsResponseException(code, body);
    }

}
