package com.challenge.graph.router;

import com.challenge.graph.Graph;
import com.challenge.graph.StringNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class TeleportationRouter implements Router {
    private TeleportationRouter() {}

    //  TODO - What is the purpose of this ctor()?  Remove??
    protected TeleportationRouter(Graph graph) {
        // Additional initialization if needed
    }

    /**
     * Recursively traverse the graph to find all nodes that can be reached from the given start node.
     * This method uses a recursive depth-first search (DFS) algorithm to traverse the graph, avoiding cycles.
     *
     * @param start
     * @param nodesToHops
     * TODO - Why return anything?
     * @return Simply return the final state of the nodesToHops input parameter.
     */
    @Override
    public Map<StringNode, Integer> reachableNodes(StringNode start,  int currentDepth, int maxDepth, Map<StringNode, Integer> nodesToHops) {
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

    @Override
    public boolean isLoopBackPossible(StringNode originNode) {
        boolean isLoopBackPossible = false;
        Set<StringNode> visited = new HashSet<>();

        for (StringNode neighbor : originNode.getNodeLinks()) {
            if (loopBackExists(originNode, neighbor, 1, visited)) {
                isLoopBackPossible = true;
                break;  // Break out as soon as a loop is discovered.
            }
        }
        return isLoopBackPossible;
    }

    private boolean loopBackExists(StringNode originNode, StringNode current, int hops, Set<StringNode> visited) {
        boolean isLoopBack = false;

        visited.add(current);
        for (StringNode neighbor : current.getNodeLinks()) {
            // Skip immediate loop back to origin on the first hop.
            if (hops <=1 && neighbor.equals(originNode)) {
                continue;
            }

            // Skip the neighbor if it has already been visited.
            if (visited.contains(neighbor)) {
                continue;
            }

            //  Succeed-fast breaker: Immediately break the loop if next neighbor matches the origin.
            if (neighbor.equals(originNode)) {
                isLoopBack = true;
            } else {
                //  recurse (descend) further.
                isLoopBack = loopBackExists(originNode, neighbor, hops + 1, visited);
            }

            if (isLoopBack) {
                break;  // Break out as soon as a loop is discovered.
            }
        }

        return isLoopBack;
    }

    /**
     * TODO - Not currently used during query execution related navigation.
     * @param start
     * @param end
     * @param maxHops
     * @return
     */
    @Override
    public Route route(StringNode start, StringNode end, int maxHops) {
        Route route = new Route();
        Set<StringNode> visited = new HashSet<>();
        if (dfs(start, end, maxHops, visited, route)) {
            return route;
        }
        return null;
    }

    /**
     * TODO - Not currently used during query execution related navigation.
     * @param current
     * @param end
     * @param remainingHops
     * @param visited
     * @param route
     * @return
     */
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
}
