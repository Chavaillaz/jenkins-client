package com.chavaillaz.jenkins.client;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.jenkins.domain.Token;
import com.chavaillaz.jenkins.domain.User;

public interface UserClient {

    String URL_CRUMB = "/crumbIssuer/api/json";
    String URL_USER = "/user/{0}/api/json";
    String URL_USER_TOKEN_GENERATION = "/user/{0}/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken";
    String URL_USER_TOKEN_REVOCATION = "/user/{0}/descriptorByName/jenkins.security.ApiTokenProperty/revoke";

    /**
     * Gets the current user information.
     *
     * @return A {@link CompletableFuture} with the user
     */
    CompletableFuture<User> getUser();

    /**
     * Generates a new authentication token for the current user.
     *
     * @param tokenName The token name
     * @return A {@link CompletableFuture} with the token
     */
    CompletableFuture<Token> generateToken(String tokenName);

    /**
     * Revokes an existing authentication token for the current user.
     *
     * @param tokenUuid The token identifier
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> revokeToken(String tokenUuid);

}
