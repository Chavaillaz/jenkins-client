package com.chavaillaz.client.jenkins;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JenkinsConstant {

    public static final String FOLDER_MODE = "com.cloudbees.hudson.plugins.folder.Folder";
    public static final String LIST_VIEW = "hudson.model.ListView";

    /**
     * Pattern to extract error messages, sometimes written by Jenkins in HTML.
     */
    public static final Pattern ERRORS_HTML_PATTERN = Pattern.compile("<h1>Error</h1><p>(.*?)</div>");

    /**
     * Pattern to extract the node number from a URL.
     */
    public static final Pattern NODE_PATTERN = Pattern.compile("node/(\\d+)/wfapi");

    /**
     * Extracts errors from the given HTML returned by Jenkins.
     *
     * @param html The content from which extract errors
     * @return The extracted errors or {@code null} in case no one was detected
     * @see #ERRORS_HTML_PATTERN
     */
    public static String extractHtmlErrors(String html) {
        return Optional.ofNullable(html)
                .map(ERRORS_HTML_PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .orElse(html);
    }

}
