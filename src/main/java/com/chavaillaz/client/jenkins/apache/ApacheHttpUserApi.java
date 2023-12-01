package com.chavaillaz.client.jenkins.apache;

import static com.chavaillaz.client.common.utility.Utils.queryFromKeyValue;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.get;
import static org.apache.hc.client5.http.async.methods.SimpleRequestBuilder.post;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.UserApi;
import com.chavaillaz.client.jenkins.domain.user.Token;
import com.chavaillaz.client.jenkins.domain.user.User;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

/**
 * Implementation of {@link UserApi} for Apache HTTP.
 */
public class ApacheHttpUserApi extends AbstractApacheHttpClient implements UserApi {

    /**
     * Creates a new user client based on Apache HTTP client.
     *
     * @param client         The Apache HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication information
     */
    public ApacheHttpUserApi(CloseableHttpAsyncClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<User> getUser() {
        return sendAsync(requestBuilder(get(), URL_USER, getAuthentication().getUsername()), User.class);
    }

    @Override
    public CompletableFuture<Token> generateToken(String tokenName) {
        return sendAsync(requestBuilder(post(), URL_USER_TOKEN_GENERATION, getAuthentication().getUsername())
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .setBody(queryFromKeyValue(Map.of("newTokenName", tokenName)), APPLICATION_JSON), Token.class);
    }

    @Override
    public CompletableFuture<Void> revokeToken(String tokenUuid) {
        return sendAsync(requestBuilder(post(), URL_USER_TOKEN_REVOCATION, getAuthentication().getUsername())
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .setBody(queryFromKeyValue(Map.of("tokenUuid", tokenUuid)), APPLICATION_JSON), Void.class);
    }

}
