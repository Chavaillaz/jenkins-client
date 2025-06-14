package com.chavaillaz.client.jenkins.domain.folder;

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

    protected List<String> folders = new LinkedList<>();

    /**
     * Creates a path composed of the given folders hierarchy.
     *
     * @param folders The hierarchy of folders
     */
    public Path(String... folders) {
        this.folders = Arrays.asList(folders);
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
