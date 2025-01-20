package com.challenge.graph;

import com.challenge.graph.router.Route;
import com.challenge.graph.router.Router;
import com.challenge.graph.router.RouterFactory;
import com.challenge.graph.utility.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    public static Integer MAX_ALLOWED_HOPS = 100; // Maximum allowed hops for routing.

    //  Use a set to enforce uniqueness of node names.
    private final Map<String, StringNode> nodesByName = new HashMap<>();
    private Router router;

    public Graph() {
        // Create the default router.
        router = RouterFactory.createRouter(RouterFactory.RouterTypes.TELEPORTATION, this);
    }

    /**
     * Add a node to the graph, ensuring uniqueness of the node name.
     * Create a new node if it doesn't already exist.
     *
     * @param name - name of the node to add.
     * @return existing or newly created node.
     */
    public StringNode addNode(String name) {
        StringNode node = nodesByName.get(name);
        if (node == null) {
            node = new StringNode(name);
            nodesByName.put(name, node);
        }
        return node;
    }

    public StringNode getNode(String name) {
        return nodesByName.get(name);
    }

    /**
     * This method is a convenience method to populate the graph with nodes and ensure they are linked together.
     * It will create new nodes if they don't already exist in the graph.
     * 
     * @param route A pair of node names representing the route to be added.
     * @see Router#route(StringNode, StringNode, int)
     */
    public void addNodeRoute(Pair<String, String> route) {
        //  addNode() will create a node if it doesn't already exist.
        StringNode nodeA = addNode(route.getValueA());
        StringNode nodeB = addNode(route.getValueB());

        //  Link the nodes together.  Node links are bidirectional.
        nodeA.link(nodeB);
        nodeB.link(nodeA);
    }

    public Set<StringNode> reachableNodes(String startName, int maxHops) {
        StringNode startNode = getNode(startName);
        if (startNode == null) {
            throw new IllegalArgumentException("Start node not found.");
        }

        //  TODO - Better map variable name?
        //  Track the number of hops required to reach each node by mapping the node to the number of hops.
        Map<StringNode, Integer> nodesToHops = new HashMap<>();
        nodesToHops.put(startNode, 0);

        //  User router to find reachable nodes.
        router.reachableNodes(startNode, 0, maxHops, nodesToHops);

        return nodesToHops.entrySet().stream()
                .filter(entry -> entry.getValue() <= maxHops)
                .map(Map.Entry::getKey)
                .filter(node ->!node.equals(startNode)) // Exclude the start node.
                .collect(Collectors.toSet());
    }

    /**
     * Is a specified destination city is reachable from a specified origin city, return the first
     * available route between them.
     *
     * Caveat:  This method simply leverages the reachableNodes() method to find all reachable nodes and then,
     *          checks if the destination city is one of those reachable nodes.
     *          TODO - Is there a more efficient way to find the unique return route?
     *
     * @param originCity - name of the city from which to start the search.
     * @param destinationCity - name of the city to reach.
     * @return Route - null if no route; otherwise the first discovered route between the cities.
     */
    public Boolean isCityReachableFrom(String originCity, String destinationCity) {
        // TODO - Why not simmply return a boolean scalar?
        return reachableNodes(originCity, MAX_ALLOWED_HOPS).contains(getNode(destinationCity));
    }

    /**
     * Determines if it's possible to loop back to the starting city without traversing and previously visited cities.
     *
     * @param originCity the name of the city from which to start the search.
     * @return Route - null if no loop; otherwise a route representing the first discovered loop.
     * @apiNote A loop does not allow return to the origin city by any portion of the destination route.
     */
    public Boolean isLoopBackPossible(String originCity) {
        // TODO - Why not simmply return a boolean scalar?
        return router.isLoopBackPossible(getNode(originCity));
    }

    // Allow default router to be overridden.
    protected void setRouter(Router router) {
        this.router = router;
    }
}
