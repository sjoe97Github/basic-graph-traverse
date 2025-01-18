package com.challenge.graph.router;

import com.challenge.graph.Graph;
import com.challenge.graph.Node;

import java.util.HashSet;
import java.util.Set;

class TeleportationRouter implements Router {
    private TeleportationRouter() {}

    protected TeleportationRouter(Graph graph) {
        // Additional initialization if needed
    }

    @Override
    public Route route(Node start, Node end, int maxHops) {
        Route route = new Route();
        Set<Node> visited = new HashSet<>();
        if (dfs(start, end, maxHops, visited, route)) {
            return route;
        }
        return null;
    }

    private boolean dfs(Node current, Node end, int remainingHops, Set<Node> visited, Route route) {
        if (remainingHops < 0) return false;
        if (visited.contains(current)) return false;

        visited.add(current);
        route.addNode(current);

        if (current.equals(end)) return true;

        for (Node neighbor : current.getConnections()) {
            if (dfs(neighbor, end, remainingHops - 1, visited, route)) {
                return true;
            }
        }

        route.getPath().removeLast();
        visited.remove(current);
        return false;
    }

    @Override
    public Set<Node> reachableNodes(Node start, int maxHops) {
        Set<Node> reachable = new HashSet<>();
        explore(start, maxHops, reachable, new HashSet<>());
        return reachable;
    }

    private void explore(Node current, int remainingHops, Set<Node> reachable, Set<Node> visited) {
        if (remainingHops < 0 || visited.contains(current)) return;
        reachable.add(current);
        visited.add(current);
        for (Node neighbor : current.getConnections()) {
            explore(neighbor, remainingHops - 1, reachable, visited);
        }
    }

    @Override
    public Route findUniqueReturnRoute(Route route) {
        Set<Node> visited = new HashSet<>(route.getPath());
        Node lastNode = route.getPath().getLast();
        Route uniqueRoute = new Route();

        if (findReturnRoute(lastNode, visited, uniqueRoute)) {
            return uniqueRoute;
        }

        return null;
    }

    private boolean findReturnRoute(Node current, Set<Node> excluded, Route uniqueRoute) {
        if (excluded.contains(current)) return false;

        uniqueRoute.addNode(current);
        excluded.add(current);

        for (Node neighbor : current.getConnections()) {
            if (findReturnRoute(neighbor, excluded, uniqueRoute)) {
                return true;
            }
        }

        uniqueRoute.getPath().removeLast();
        excluded.remove(current);
        return false;
    }
}
