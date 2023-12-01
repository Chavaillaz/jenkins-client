package com.chavaillaz.client.jenkins.vertx;

import static com.chavaillaz.client.common.vertx.VertxUtils.formData;
import static io.vertx.core.http.HttpMethod.GET;
import static io.vertx.core.http.HttpMethod.POST;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.UserApi;
import com.chavaillaz.client.jenkins.domain.user.Token;
import com.chavaillaz.client.jenkins.domain.user.User;
import io.vertx.ext.web.client.WebClient;

/**
 * Implementation of {@link UserApi} for Vert.x HTTP.
 */
public class VertxHttpUserApi extends AbstractVertxHttpClient implements UserApi {

    /**
     * Creates a new user client based on Vert.x HTTP client.
     *
     * @param client         The Vert.x HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication information
     */
    public VertxHttpUserApi(WebClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<User> getUser() {
        return handleAsync(requestBuilder(GET, URL_USER, getAuthentication().getUsername()).send(), User.class);
    }

    @Override
    public CompletableFuture<Token> generateToken(String tokenName) {
        return handleAsync(requestBuilder(POST, URL_USER_TOKEN_GENERATION, getAuthentication().getUsername())
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .sendForm(formData(Map.of("newTokenName", tokenName))), Token.class);
    }

    @Override
    public CompletableFuture<Void> revokeToken(String tokenUuid) {
        return handleAsync(requestBuilder(POST, URL_USER_TOKEN_REVOCATION, getAuthentication().getUsername())
                .putHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .sendForm(formData(Map.of("tokenUuid", tokenUuid))), Void.class);
    }

}
