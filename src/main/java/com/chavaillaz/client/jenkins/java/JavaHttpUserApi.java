package com.chavaillaz.client.jenkins.java;

import static com.chavaillaz.client.common.java.JavaHttpUtils.ofFormData;

import java.net.http.HttpClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.UserApi;
import com.chavaillaz.client.jenkins.domain.user.Token;
import com.chavaillaz.client.jenkins.domain.user.User;

/**
 * Implementation of {@link UserApi} for Java HTTP.
 */
public class JavaHttpUserApi extends AbstractJavaHttpClient implements UserApi {

    /**
     * Creates a new user client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication information
     */
    public JavaHttpUserApi(HttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<User> getUser() {
        return sendAsync(requestBuilder(URL_USER, getAuthentication().getUsername()).GET(), User.class);
    }

    @Override
    public CompletableFuture<Token> generateToken(String tokenName) {
        return sendAsync(requestBuilder(URL_USER_TOKEN_GENERATION, getAuthentication().getUsername())
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .POST(ofFormData(Map.of("newTokenName", tokenName))), Token.class);
    }

    @Override
    public CompletableFuture<Void> revokeToken(String tokenUuid) {
        return sendAsync(requestBuilder(URL_USER_TOKEN_REVOCATION, getAuthentication().getUsername())
                .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .POST(ofFormData(Map.of("tokenUuid", tokenUuid))), Void.class);
    }

}
