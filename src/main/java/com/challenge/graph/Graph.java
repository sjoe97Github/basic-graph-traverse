package com.challenge.graph;

import com.challenge.graph.router.Route;
import com.challenge.graph.router.Router;
import com.challenge.graph.router.RouterFactory;
import com.challenge.graph.utility.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    //  Use a set to enforce uniqueness of node names.
    private final Map<String, Node> nodesByName = new HashMap<>();
    private Router router;

    public Graph() {
        // Default router is TeleportationRouter.
        router = RouterFactory.createRouter(RouterFactory.RouterTypes.TELEPORTATION, this);
    }

    /**
     * Add a node to the graph, ensuring uniqueness of the node name.
     * Create a new node if it doesn't already exist.
     *
     * @param name - name of the node to add.
     * @return existing or newly created node.
     */
    public Node addNode(String name) {
        Node node = nodesByName.get(name);
        if (node == null) {
            node = new Node(name);
            nodesByName.put(name, node);
        }
        return node;
    }

    public Node getNode(String name) {
        return nodesByName.get(name);
    }

    /**
     * This method is a convenience method to populate the graph with nodes and ensure they are linked together.
     * It will create new nodes if they don't already exist in the graph.
     * 
     * @param route A pair of node names representing the route to be added.
     */
    public void addNodeRoute(Pair<String, String> route) {
        //  addNode() will create a node if it doesn't already exist.
        Node nodeA = addNode(route.getValueA());
        Node nodeB = addNode(route.getValueB());

        //  Link the nodes together.  Node links are bidirectional.
        nodeA.link(nodeB);
        nodeB.link(nodeA);
    }

    // Provide a way to specify a different router.
    public void setRouter(Router router) {
        this.router = router;
    }

    public Route findRoute(String startName, String endName, int maxHops) {
        Node start = getNode(startName);
        Node end = getNode(endName);
        if (start == null) {
            throw new IllegalArgumentException(String.format("Route Error: Starting node doesn't exist, node-name= %s!", startName));
        } else if (end == null) {
            throw new IllegalArgumentException(String.format("Route Error: End node doesn't exist, node-name= %s!", endName));
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
