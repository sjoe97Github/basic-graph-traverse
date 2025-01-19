package com.challenge.graph;

import com.challenge.graph.utility.Pair;

import java.util.Set;

/**
 * Populates a graph from a collection of Node Pairs.
 */
public class GraphBuilder {
    private final Set<Pair<String, String>> nodeNameRoutes;
    private final Graph graph;

    public GraphBuilder(Set<Pair<String, String>> nodeNameRoutes) {
        this.nodeNameRoutes = nodeNameRoutes;
        this.graph = new Graph();
    }

    public Graph build() {
        nodeNameRoutes.forEach(graph::addNodeRoute);
        return graph;
    }
}
