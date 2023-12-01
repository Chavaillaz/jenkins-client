package com.chavaillaz.client.jenkins.okhttp;

import static com.chavaillaz.client.common.okhttp.OkHttpUtils.ofFormData;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.JenkinsAuthentication;
import com.chavaillaz.client.jenkins.api.UserApi;
import com.chavaillaz.client.jenkins.domain.user.Token;
import com.chavaillaz.client.jenkins.domain.user.User;
import okhttp3.OkHttpClient;

/**
 * Implementation of {@link UserApi} for OkHttp.
 */
public class OkHttpUserApi extends AbstractOkHttpClient implements UserApi {

    /**
     * Creates a new user client based on OkHttp client.
     *
     * @param client         The OkHttp client to use
     * @param baseUrl        The URL of Jenkins
     * @param authentication The authentication information
     */
    public OkHttpUserApi(OkHttpClient client, String baseUrl, JenkinsAuthentication authentication) {
        super(client, baseUrl, authentication);
    }

    @Override
    public CompletableFuture<User> getUser() {
        return sendAsync(requestBuilder(URL_USER, getAuthentication().getUsername()).get(), User.class);
    }

    @Override
    public CompletableFuture<Token> generateToken(String tokenName) {
        return sendAsync(requestBuilder(URL_USER_TOKEN_GENERATION, getAuthentication().getUsername())
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .post(ofFormData(Map.of("newTokenName", tokenName))), Token.class);
    }

    @Override
    public CompletableFuture<Void> revokeToken(String tokenUuid) {
        return sendAsync(requestBuilder(URL_USER_TOKEN_REVOCATION, getAuthentication().getUsername())
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_FORM)
                .post(ofFormData(Map.of("tokenUuid", tokenUuid))), Void.class);
    }

}
