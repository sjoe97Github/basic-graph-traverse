package com.scottj.graph;

import com.scottj.graph.interfaces.Node;

public class NodePair {
    private final Node first;
    private final Node second;

    public NodePair(Node first, Node second) {
        this.first = first;
        this.second = second;
    }

    public Node otherNode(Node node) {
        if (node.equals(first)) {
            return second;
        } else if (node.equals(second)) {
            return first;
        } else {
            throw new IllegalArgumentException(String.format("The provided node, %s, is not in the NodePair.", node.getName()));
        }
    }
}
