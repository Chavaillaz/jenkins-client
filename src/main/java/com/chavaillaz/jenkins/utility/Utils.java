package com.chavaillaz.jenkins.utility;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Base64;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    /**
     * Converts a timestamp in milliseconds to a date and time.
     *
     * @param milliseconds The timestamp milliseconds
     * @return The corresponding date and time
     */
    public static OffsetDateTime dateTimeFromMs(long milliseconds) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }

    /**
     * Reads efficiently an input stream and sends it back as {@link String}.
     * Note that it expects the stream to be encoded in UTF8.
     *
     * @param inputStream The input stream to read
     * @return THe corresponding string
     */
    @SneakyThrows
    public static String inputStreamToString(InputStream inputStream) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = inputStream.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        return result.toString(UTF_8);
    }

    /**
     * Encodes a {@link String} to base 64 format.
     *
     * @param value The value to encode
     * @return The encoded value
     */
    public static String encodeBase64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

}
