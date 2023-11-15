package com.chavaillaz.jenkins.client;

import static com.chavaillaz.jenkins.client.JenkinsConstant.DEFAULT_OBJECT_MAPPER;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.net.URI;
import java.text.MessageFormat;

import com.chavaillaz.jenkins.domain.Crumb;
import com.chavaillaz.jenkins.exception.DeserializationException;
import com.chavaillaz.jenkins.exception.SerializationException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@Getter
public abstract class AbstractHttpClient {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_COOKIE = "Cookie";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_JSON = "application/json";
    public static final String HEADER_CONTENT_XML = "application/xml";
    public static final String HEADER_CONTENT_FORM = "application/x-www-form-urlencoded";

    protected final String baseUrl;
    protected final ObjectMapper objectMapper;
    protected final Authentication authentication;

    protected AbstractHttpClient(String baseUrl, Authentication authentication) {
        this.baseUrl = baseUrl;
        this.objectMapper = buildObjectMapper();
        this.authentication = authentication;
    }

    /**
     * Creates an object mapper that will be used to serialize and deserialize all objects.
     *
     * @return The object mapper
     */
    protected ObjectMapper buildObjectMapper() {
        return DEFAULT_OBJECT_MAPPER;
    }

    /**
     * Gets the crumb for the current user from Jenkins according to
     * <a href="https://www.jenkins.io/doc/book/security/csrf-protection/">CSRF protection</a>.
     *
     * @return The loaded crumb
     */
    protected abstract Crumb loadCrumb();

    /**
     * Creates a URL and replaces the parameters in it with the given method parameters.
     * Note that by giving a full URL (with scheme) it will not add the base URL to it.
     *
     * @param url        The URL with possible parameters in it (using braces like {0}, {1}, ...)
     * @param parameters The parameters value to replace in the URL (in the right order)
     * @return The final URL incorporating parameters values
     */
    protected URI url(String url, Object... parameters) {
        return URI.create((url.startsWith("http") ? EMPTY : this.baseUrl) + MessageFormat.format(url, parameters));
    }

    /**
     * Deserializes a JSON content to the given type.
     *
     * @param content The object to deserialize
     * @param type    The object class type
     * @param <T>     The object type
     * @return The object instance of the given type
     */
    public <T> T deserialize(String content, JavaType type) {
        if (type.getRawClass() == Void.class || isBlank(content)) {
            return null;
        }

        try {
            return objectMapper.readValue(content, type);
        } catch (Exception e) {
            throw new DeserializationException(content, type, e);
        }
    }

    /**
     * Serializes an object to JSON.
     *
     * @param content The object to serialize
     * @return The corresponding JSON value
     */
    public String serialize(Object content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (Exception e) {
            throw new SerializationException(content, e);
        }
    }

}
