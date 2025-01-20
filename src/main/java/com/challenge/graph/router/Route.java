package com.challenge.graph.router;

import com.challenge.graph.StringNode;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class Route {
    private final LinkedList<StringNode> path;

    public Route() {
        this.path = new LinkedList<>();
    }

    public void addNode(StringNode node) {
        path.add(node);
    }

    public LinkedList<StringNode> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Route: " + path.stream()
                .map(StringNode::getName)
                .collect(Collectors.joining(" - "));
    }
}
