package com.challenge.graph.router;

import com.challenge.graph.Node;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class Route {
    private final LinkedList<Node> path;

    public Route() {
        this.path = new LinkedList<>();
    }

    public void addNode(Node node) {
        path.add(node);
    }

    public LinkedList<Node> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Route: " + path.stream()
                .map(Node::getName)
                .collect(Collectors.joining(" - "));
    }
}
