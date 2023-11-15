package com.chavaillaz.jenkins.exception;

public class JenkinsClientException extends RuntimeException {

    /**
     * Creates a new generic Jenkins client exception.
     */
    public JenkinsClientException() {
        super();
    }

    /**
     * Creates a new generic Jenkins client exception.
     *
     * @param message The error message
     */
    public JenkinsClientException(String message) {
        super(message);
    }

    /**
     * Creates a new generic Jenkins client exception.
     *
     * @param message The error message
     * @param cause   The root exception
     */
    public JenkinsClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new generic Jenkins client exception.
     *
     * @param cause The root exception
     */
    public JenkinsClientException(Throwable cause) {
        super(cause);
    }

}
