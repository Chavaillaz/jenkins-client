package com.chavaillaz.jenkins.client.java;

import static com.chavaillaz.jenkins.client.java.JavaHttpUtils.ofFormData;

import java.net.http.HttpClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jenkins.client.Authentication;
import com.chavaillaz.jenkins.client.UserClient;
import com.chavaillaz.jenkins.domain.Token;
import com.chavaillaz.jenkins.domain.User;

public class JavaHttpUserClient extends AbstractJavaHttpClient implements UserClient {

    /**
     * Creates a new user client based on Java HTTP client.
     *
     * @param client         The Java HTTP client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication header (nullable)
     */
    public JavaHttpUserClient(HttpClient client, String baseUrl, Authentication authentication) {
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
