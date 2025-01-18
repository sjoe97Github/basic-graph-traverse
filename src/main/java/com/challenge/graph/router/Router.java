package com.challenge.graph.router;

import com.challenge.graph.Node;

import java.util.Set;

public interface Router {
    Route route(Node start, Node end, int maxHops);
    Set<Node> reachableNodes(Node start, int maxHops);
    Route findUniqueReturnRoute(Route route);
}
