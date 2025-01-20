package com.challenge.graph;

import com.challenge.graph.utility.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
class GraphTest extends BaseGraphTest{
    Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();

        // Populate the graph using the links provided in the input file.
        baseParser.getLinks().forEach(route -> {
            graph.addNodeRoute(route);
        });
    }

    @Test
    void addNodeRoute() {
        // Test that adding a route between two nodes creates a bidirectional link.
        Graph graph = new Graph();
        graph.addNodeRoute(new Pair<>("A", "B"));
        assertTrue(graph.getNode("A").isLinkedTo(graph.getNode("B")));
        assertTrue(graph.getNode("B").isLinkedTo(graph.getNode("A")));
    }

    @Test
    void testGraphPopulation() {
        // Verify that all nodes and links were correctly added.  Loop through the baseParser's links and check
        // if the graph contains the expected bidirectional links.
        baseParser.getLinks().forEach(route -> {
            var nodeA = graph.getNode(route.getValueA());
            var nodeB = graph.getNode(route.getValueB());
            assertAll(() -> {
               assertNotNull(nodeA);
               assertNotNull(nodeB);
               assertTrue(nodeA.isLinkedTo(nodeB));
               assertTrue(nodeB.isLinkedTo(nodeA));
            });
        });
    }

    @Test
    void testReachableNodes() {
        // Test that the reachableNodes method returns the expected set of nodes for a given start node.
        // Leverage the reachableNodes method in the Graph class to achieve this, specifying a maxHops value larger than
        // the maximum allowed hops.
        var reachableNodes = graph.reachableNodes("washington", Graph.MAX_ALLOWED_HOPS + 1);
        assertEquals(5, reachableNodes.size());
        assertTrue(reachableNodes.stream()
                        .map(StringNode::getName)
                        .collect(Collectors.toSet())
                        .containsAll(Set.of("atlanta", "baltimore", "philadelphia", "new york", "seattle")));
    }

    @Test
    void testReachableNodesWithinOneHop() {
        var reachableNodes = graph.reachableNodes("washington", 1);
        assertEquals(2, reachableNodes.size());
        assertTrue(reachableNodes.stream()
                .map(StringNode::getName)
                .collect(Collectors.toSet())
                .containsAll(Set.of("atlanta", "baltimore")));
    }

    @Test
    void testReachableNodesWithinTwoHops() {
        var reachableNodes = graph.reachableNodes("washington", 2);
        assertEquals(4, reachableNodes.size());
        assertTrue(reachableNodes.stream()
                .map(StringNode::getName)
                .collect(Collectors.toSet())
                .containsAll(Set.of("atlanta", "baltimore", "philadelphia", "seattle")));
    }

    @Test
    void testIsCityReachable() {
        assertTrue(graph.isCityReachableFrom("washington", "new york"));
        assertTrue(graph.isCityReachableFrom("washington", "seattle"));
        assertFalse(graph.isCityReachableFrom("washington", "san fransisco"));
        assertFalse(graph.isCityReachableFrom("washington", "San fransisco"));
    }
}