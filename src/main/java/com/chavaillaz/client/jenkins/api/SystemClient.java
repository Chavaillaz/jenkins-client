package com.chavaillaz.client.jenkins.api;

import java.util.concurrent.CompletableFuture;

import com.chavaillaz.client.jenkins.domain.SystemInfo;

public interface SystemClient {

    String URL_SYSTEM = "/";
    String URL_QUIET_DOWN_START = "/quietDown";
    String URL_QUIET_DOWN_CANCELLATION = "/cancelQuietDown";
    String URL_RESTART = "/restart";
    String URL_RESTART_SAFE = "/safeRestart";
    String URL_EXIT = "/exit";
    String URL_EXIT_SAFE = "/safeExit";
    String URL_RELOAD = "/reload";

    /**
     * Gets the system information of Jenkins.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<SystemInfo> getSystemInfo();

    /**
     * Puts Jenkins in a quiet mode, in preparation for a restart.
     * In that mode Jenkins don't start any build.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> quietDown();

    /**
     * Cancels the effect of the {@link #quietDown()} command.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> cancelQuietDown();

    /**
     * Restarts Jenkins immediately, cancelling running builds.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> restart();

    /**
     * Puts Jenkins into the quiet mode, wait for existing builds to be completed, and then restart Jenkins.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> safeRestart();

    /**
     * Shutdowns Jenkins immediately, cancelling running builds.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> exit();

    /**
     * Puts Jenkins into the quiet mode, waits for existing builds to be completed, and then shutdowns Jenkins.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> safeExit();

    /**
     * Refreshes Jenkins configuration files and directory structure from disk.
     * Note that a restart is not necessary after this operation.
     *
     * @return A {@link CompletableFuture} without content
     */
    CompletableFuture<Void> reload();

}
