package com.challenge;

import com.challenge.graph.Graph;
import com.challenge.graph.GraphBuilder;
import com.challenge.graph.queries.GraphQuery;
import com.challenge.graph.InputFileParser;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * The main entry point for the TeleportApplication.
 * This application processes graph links and queries, builds a graph based on the links,
 * and executes the queries on the graph.
 *
 * The input can be provided either as a file path argument or piped through standard input.
 *
 * @throws IllegalArgumentException if the specified input file does not exist.
 * @throws RuntimeException if there's an error parsing the input.
 */
public class TeleportApplication {
    public static void main(String[] args) {
        InputFileParser parser = new InputFileParser();
        
        try {
            if (args.length > 0) {
                // Assume the argument contains an input file.
                File file = new File(args[0]);
                if (!file.exists()) {
                    throw new IllegalArgumentException("File does not exist: " + args[0]);
                }
                parser.parse(file);
            } else if (System.in.available() > 0) {
                // Piped or redirected input:
                // The BufferedReader will read from System.in, so we don't need to close it.  Therefore, I did
                // not introduce an overloaded parse method that takes a BufferedReader or the InputStreamReader.
                // Instead, I just wrap the BufferedReader around System.in and call an overloaded parse method that
                // takes a Stream of Strings.
                // Note that I could have called the parseLines method but the intent is not to expose any but the
                // parse methods.
                parser.parse(new BufferedReader(new InputStreamReader(System.in)).lines());
            } else {
                System.out.println("Usage: java -jar TeleportApplication <input_file_path>");
                System.out.println("   or: cat input.txt | java -jar TeleportApplication");
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read input: " + e.getMessage(), e);
        }

        Graph graph = (new GraphBuilder(parser.getLinks())).build();
        TeleportApplication teleportApplication = new TeleportApplication(graph);

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