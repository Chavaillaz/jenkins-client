package com.chavaillaz.jenkins.exception;

public class SerializationException extends JenkinsClientException {

    /**
     * Creates a new serialization exception.
     *
     * @param object    The content to serialize
     * @param exception The exception thrown by Jackson
     */
    public SerializationException(Object object, Throwable exception) {
        super("Unable to serialize type " + object.getClass().getSimpleName() + " from " + object, exception);
    }

}
