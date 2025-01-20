package com.challenge.graph.router;

import com.challenge.graph.Graph;
import com.challenge.graph.StringNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class TeleportationRouter implements Router {
    private TeleportationRouter() {}

    protected TeleportationRouter(Graph graph) {
        // Additional initialization if needed
    }

    @Override
    public Route route(StringNode start, StringNode end, int maxHops) {
        Route route = new Route();
        Set<StringNode> visited = new HashSet<>();
        if (dfs(start, end, maxHops, visited, route)) {
            return route;
        }
        return null;
    }

    private boolean dfs(StringNode current, StringNode end, int remainingHops, Set<StringNode> visited, Route route) {
        if (remainingHops < 0) return false;
        if (visited.contains(current)) return false;

        visited.add(current);
        route.addNode(current);

        if (current.equals(end)) return true;

        for (StringNode neighbor : current.getNodeLinks()) {
            if (dfs(neighbor, end, remainingHops - 1, visited, route)) {
                return true;
            }
        }

        route.getPath().removeLast();
        visited.remove(current);
        return false;
    }

    /**
     * Recursively traverse the graph to find all nodes that can be reached from the given start node.
     * This method uses a recursive depth-first search (BFS) algorithm to traverse the graph, avoiding cycles.
     *
     * @param start
     * @param nodesToHops
     * @return the
     */
    @Override
    public Map<StringNode, Integer> reachableNodes(StringNode start,  int currentDepth, int maxDepth, Map<StringNode, Integer> nodesToHops) {
        Set<StringNode> reachable = new HashSet<>();

        if (currentDepth < maxDepth) {
            start.getNodeLinks().forEach(neighbor -> {
                if (!nodesToHops.containsKey(neighbor)) {
                    nodesToHops.put(neighbor, currentDepth);
                    reachableNodes(neighbor, currentDepth + 1, maxDepth, nodesToHops);
                }
            });
        }

        return nodesToHops;
    }

    private void explore(StringNode current, int remainingHops, Set<StringNode> reachable, Set<StringNode> visited) {
        if (remainingHops < 0 || visited.contains(current)) return;
        reachable.add(current);
        visited.add(current);
        for (StringNode neighbor : current.getNodeLinks()) {
            explore(neighbor, remainingHops - 1, reachable, visited);
        }
    }

    @Override
    public Set<Route> findAllRoutesBetweenNodes(StringNode start, StringNode end) {
        return Set.of();
    }

    @Override
    public Route findUniqueReturnRoute(Route route) {
        Set<StringNode> visited = new HashSet<>(route.getPath());
        StringNode lastNode = route.getPath().getLast();
        Route uniqueRoute = new Route();

        if (findReturnRoute(lastNode, visited, uniqueRoute)) {
            return uniqueRoute;
        }

        return null;
    }

    private boolean findReturnRoute(StringNode current, Set<StringNode> excluded, Route uniqueRoute) {
        if (excluded.contains(current)) return false;

        uniqueRoute.addNode(current);
        excluded.add(current);

        for (StringNode neighbor : current.getNodeLinks()) {
            if (findReturnRoute(neighbor, excluded, uniqueRoute)) {
                return true;
            }
        }

        uniqueRoute.getPath().removeLast();
        excluded.remove(current);
        return false;
    }
}
