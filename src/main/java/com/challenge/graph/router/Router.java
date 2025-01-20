package com.challenge.graph.router;

import com.challenge.graph.StringNode;

import java.util.Map;
import java.util.Set;

public interface Router {
    Route route(StringNode start, StringNode end, int maxHops);
    Set<Route> findAllRoutesBetweenNodes(StringNode start, StringNode end);
    Map<StringNode, Integer> reachableNodes(StringNode start, int currentDepth, int maxDepth, Map<StringNode, Integer> nodesToHops);
    Route findUniqueReturnRoute(Route route);
}
