package com.chavaillaz.jenkins.domain;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Path {

    /**
     * Represents the root folder of Jenkins.
     */
    public static final Path ROOT = new Path();

    private List<String> folders = new LinkedList<>();

    /**
     * Represents a path composed by the given folders hierarchy.
     *
     * @param folder The hierarchy of folders
     * @return The corresponding path
     */
    public static Path folders(String... folder) {
        return new Path(Arrays.asList(folder));
    }

    /**
     * Gets the URL representing the path (and therefore folders hierarchy).
     *
     * @return The path URL
     */
    public String getUrl() {
        return folders.stream()
                .map(folder -> "/job/" + folder)
                .collect(joining());
    }

    @Override
    public String toString() {
        return getUrl();
    }

}
