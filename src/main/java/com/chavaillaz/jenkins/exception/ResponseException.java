package com.chavaillaz.jenkins.exception;

import static com.chavaillaz.jenkins.client.JenkinsConstant.extractHtmlErrors;

import lombok.Getter;

@Getter
public class ResponseException extends JenkinsClientException {

    private final Integer statusCode;
    private final String body;

    /**
     * Creates a new Jenkins response exception, meaning the request didn't return a success code.
     * The exception message is computed based on the given body, trying to parse error messages in HTML.
     *
     * @param statusCode The status code
     * @param body       The content body
     */
    public ResponseException(int statusCode, String body) {
        super(errorMessage(statusCode, body));
        this.statusCode = statusCode;
        this.body = body;
    }

    private static String errorMessage(Integer statusCode, String content) {
        return "Jenkins responded with " + statusCode + ": " + extractHtmlErrors(content);
    }

}
