package com.chavaillaz.jenkins.client.java;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JavaHttpUtils {

    /**
     * Creates a body publisher for form data using a map of key value representing the data to send.
     *
     * @param data The data to format and send as form data
     * @return The corresponding body publisher
     */
    public static BodyPublisher ofFormData(Map<Object, Object> data) {
        return BodyPublishers.ofString(queryFromKeyValue(data));
    }

    /**
     * Creates a query representing the given data.
     * For example, {@code Map.of("border", "orange", "background", "black")}
     * will be {@code border=orange&background=black}.
     *
     * @param data The data to format
     * @return the corresponding {@link String}
     */
    public static String queryFromKeyValue(Map<Object, Object> data) {
        StringBuilder result = new StringBuilder();
        data.forEach((key, value) -> {
            if (!result.isEmpty()) {
                result.append("&");
            }
            String encodedName = encode(key.toString(), UTF_8);
            String encodedValue = encode(value.toString(), UTF_8);
            result.append(encodedName);
            if (encodedValue != null) {
                result.append("=");
                result.append(encodedValue);
            }
        });
        return result.toString();
    }

}
