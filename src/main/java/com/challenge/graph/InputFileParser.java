package com.challenge.graph;

import com.challenge.graph.queries.GraphQuery;
import com.challenge.graph.queries.CitiesFromQuery;
import com.challenge.graph.queries.LoopPossibleQuery;
import com.challenge.graph.queries.TeleportFromQuery;
import com.challenge.graph.utility.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class InputFileParser {
    //  Use a Set to eliminate duplicate links
    private final Set<Pair<String, String>> links = new HashSet<>();

    //  Maintain the order of the queries parsed from the input file
    private final List<GraphQuery> queries = new LinkedList<>();

    public void parse(File file) {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            parseLines(lines);
        } catch (IOException ex) {
            throw new RuntimeException(String.format("Input File Parsing Error: I/O error while attempting to read input file, %s", file.getAbsolutePath()), ex);
        }
    }

    // Simply a public overload to keep the interface simple and not have to expose the "internal" parseLines() method.
    public void parse(Stream<String> lines) {
        parseLines(lines);
    }

    /**
     * The primary purpose of this method it to separate the file system specifics of obtaining the stream of lines
     * from the parsing of the lines.
     *
     * At the very least, separating file management from parsing allows unit tests to focus on the parsing logic rather
     * than anything related to where to find, create, or otherwise manage files contained test data.
     *
     * It might also be true that this separation is a step in the right direction from a decoupled design perspective?
     *
     * @param lines a stream of lines to parse.
     */
    protected void parseLines(Stream<String> lines) {
        lines.forEach(this::parseLine);
    }

    public Set<Pair<String, String>> getLinks() {
        return links;
    }

    public List<GraphQuery> getQueries() {
        return queries;
    }

    protected void parseLine(String line) {
        // check if line is null or empty
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Input File Parsing Error: Null or empty input line!");
        }

        if (line.startsWith("cities")) {
            queries.add(new CitiesFromQuery(line).prepare());
        } else if (line.startsWith("can")){
            queries.add(new TeleportFromQuery(line).prepare());
        } else if (line.startsWith("loop")) {
            queries.add(new LoopPossibleQuery(line).prepare());
        } else if (line.contains("-")) {
            String[] parts = line.split("-");

            // A length < 2 test might be sufficient, but any length other than 2 indicates a problem with the input line format.
            if (parts.length != 2) {
                throw new IllegalArgumentException("Input File Parsing Error: Invalid City to City route format, line=" + line);
            }
            links.add(new Pair<>(parts[0].trim(), parts[1].trim()));
        } else {
            throw new IllegalArgumentException("Input File Parsing Error: Invalid input line format, line=" + line);
        }
    }
}

