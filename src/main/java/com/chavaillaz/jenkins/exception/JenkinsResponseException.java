package com.chavaillaz.jenkins.exception;

import static com.chavaillaz.jenkins.client.JenkinsConstant.extractHtmlErrors;

import com.chavaillaz.client.exception.ResponseException;
import lombok.Getter;

@Getter
public class JenkinsResponseException extends ResponseException {

    /**
     * Creates a new Jenkins response exception, meaning the request didn't return a success code.
     * The exception message is computed based on the given body, trying to parse error messages in HTML.
     *
     * @param statusCode The status code
     * @param body       The content body
     */
    public JenkinsResponseException(int statusCode, String body) {
        super(statusCode, body, errorMessage(statusCode, body));
    }

    private static String errorMessage(Integer statusCode, String content) {
        return "Jenkins responded with " + statusCode + ": " + extractHtmlErrors(content);
    }

}
