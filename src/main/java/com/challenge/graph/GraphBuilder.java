package com.challenge.graph;

import com.challenge.graph.router.Router;
import com.challenge.graph.utility.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Minimalistic graph builder.
 */
public class GraphBuilder {
    private Set<Pair<String, String>> nodeNameRoutes;
    private Router router;
    private Graph graph;

    public GraphBuilder(Set<Pair<String, String>> nodeNameRoutes) {
        this.graph = new Graph();
        this.nodeNameRoutes = nodeNameRoutes;
    }

    public GraphBuilder() {
        this.graph = new Graph();
        this.nodeNameRoutes = new HashSet<>();
    }

    public GraphBuilder addNodeRoutes(Set<Pair<String, String>> nodeNameRoutes) {
        this.nodeNameRoutes = nodeNameRoutes;
        return this;
    }

    public GraphBuilder addNodeRoute(Pair<String, String> route) {
        this.nodeNameRoutes.add(route);
        return this;
    }

    public GraphBuilder addNodeRoute(String startName, String endName) {
        this.nodeNameRoutes.add(new Pair<>(startName, endName));
        return this;
    }

    public GraphBuilder setRouter(Router router) {
        this.router = router;
        return this;
    }

    public Graph build() {
        if (this.router != null) {
            this.graph.setRouter(this.router);
        }

        // populate the graph with node routes
        this.nodeNameRoutes.forEach(graph::addNodeRoute);
        return this.graph;
    }
}
