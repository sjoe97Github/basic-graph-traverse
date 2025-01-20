package com.challenge.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testNodeCannotLinkToItself() {
        // Test that a node cannot be linked to itself.
        StringNode node = new StringNode("Test");
        assertThrows(IllegalArgumentException.class, () -> node.link(node));
    }

    @Test
    void testMultipleNodesCanBeLinked() {
        // Test that nodes can be linked to each other.
        StringNode nodeA = new StringNode("A");
        StringNode nodeB = new StringNode("B");
        StringNode nodeC = new StringNode("C");
        nodeA.link(nodeB);
        nodeA.link(nodeC);
        assertTrue(nodeA.getNodeLinks().contains(nodeB));
        assertTrue(nodeA.getNodeLinks().contains(nodeC));
    }

    @Test
    void testStringNodesEquivalentBasedOnName() {
        // Test that two StringNodes are considered equal if they have the same name.
        StringNode nodeA = new StringNode("A");
        StringNode nodeOtherA = new StringNode("A");
        assertEquals(nodeA, nodeOtherA);
    }
}