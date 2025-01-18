package com.challenge.graph;

import com.challenge.graph.router.Route;
import com.challenge.graph.router.Router;
import com.challenge.graph.router.RouterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();
    private Router router;

    public Graph() {
        // Default router is TeleportationRouter.
        router = RouterFactory.createRouter(RouterFactory.RouterTypes.TELEPORTATION, this);
    }

    // Provide a way to specify a different router.
    // TODO - Consider a graph factory or builder that establishes the router during graph instantiation and don't allow changing the router later.
    public void replaceDefaultRouter(Router router) {
        this.router = router;
    }

    public Node addNode(String name) {
        if (nodes.containsKey(name)) {
            throw new IllegalArgumentException("Node with name " + name + " already exists.");
        }
        Node node = new Node(name);
        nodes.put(name, node);
        return node;
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public void linkNodes(String name1, String name2) {
        Node node1 = getNode(name1);
        Node node2 = getNode(name2);
        if (node1 == null || node2 == null) {
            throw new IllegalArgumentException("One or both nodes not found.");
        }
        node1.link(node2);
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Route findRoute(String startName, String endName, int maxHops) {
        Node start = getNode(startName);
        Node end = getNode(endName);
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end node not found.");
        }
        return router.route(start, end, maxHops);
    }

    public Route findRoute(String startName, String endName) {
        return findRoute(startName, endName, 1);
    }

    public Set<Node> reachableNodes(String startName, int maxHops) {
        Node start = getNode(startName);
        if (start == null) {
            throw new IllegalArgumentException("Start node not found.");
        }
        return router.reachableNodes(start, maxHops);
    }

    public Route uniqueReturnRoute(Route route) {
        return router.findUniqueReturnRoute(route);
    }
}
