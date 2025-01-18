package com.challenge;

import com.challenge.graph.Graph;
import com.challenge.graph.Node;
import com.challenge.graph.queries.GraphQuery;
import com.challenge.graph.utility.Pair;
import com.challenge.input.InputFileParser;

import javax.management.Query;
import java.io.File;
import java.io.IOException;

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

        Graph graph = new Graph();

        for (Pair<String, String> link : parser.getLinks()) {
            Node node1 = graph.addNode(link.getValueA());
            Node node2 = graph.addNode(link.getValueB());
            graph.linkNodes(node1.getName(), node2.getName());
        }

        for (GraphQuery query : parser.getQueries()) {
            query.execute(graph);
            System.out.println(query.getResult());
        }
    }
}

