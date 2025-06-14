package com.chavaillaz.client.jenkins.utility;

import static com.chavaillaz.client.common.utility.Utils.readInputStream;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

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
     * Reads an {@link InputStream} and returns its content as a {@link String} using UTF-8 encoding.
     *
     * @param inputStream The input stream to read
     * @return The content of the input stream
     * @throws RuntimeException If an I/O error occurs
     */
    public static String uncheckReadInputStream(InputStream inputStream) {
        try {
            return readInputStream(inputStream, UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}