package com.challenge;

import com.challenge.graph.Graph;
import com.challenge.graph.GraphBuilder;
import com.challenge.graph.queries.GraphQuery;
import com.challenge.graph.InputFileParser;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

public class TeleportApplication {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TeleportApplication <input_file_path>");
            return;
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + args[0]);
        }

        InputFileParser parser = new InputFileParser();
        try {
            parser.parse(file);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse file: " + e.getMessage(), e);
        }

        Graph graph = (new GraphBuilder(parser.getLinks())).build();

        TeleportApplication teleportApplication = new TeleportApplication(graph);

        // There really isn't a need to capture the processing stream to the processQueryResults variable; however,
        // doing so provides more clarity that the processor is actually performing queries and returning query
        // results in a stream for further processing, logging, or simply output to the console as is done here.
        Stream<String> processedQueryResults = teleportApplication.processQueries(parser.getQueries());
        processedQueryResults.forEach(System.out::println);
    }

    private final Graph graph;
    public TeleportApplication(Graph graph) {
        this.graph = graph;
    }

    protected Stream<String> processQueries(List<GraphQuery> queries) {
        return queries.stream().map(query -> query.prepare().execute(graph));
    }
}

