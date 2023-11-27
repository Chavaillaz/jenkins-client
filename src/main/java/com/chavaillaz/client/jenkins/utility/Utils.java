package com.chavaillaz.client.jenkins.utility;

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

}