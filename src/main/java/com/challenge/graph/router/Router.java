package com.challenge.graph.router;

import com.challenge.graph.StringNode;

import java.util.Map;
import java.util.Set;

public interface Router {
    Route route(StringNode start, StringNode end, int maxHops);
    Map<StringNode, Integer> reachableNodes(StringNode start, int currentDepth, int maxDepth, Map<StringNode, Integer> nodesToHops);
    boolean isLoopBackPossible(StringNode start);
}
