package com.scottj.graph.teleport.links;

import com.scottj.graph.NodePair;
import com.scottj.graph.interfaces.Link;
import com.scottj.graph.interfaces.Node;

/**
 * Links are not directed and a node cannot be linked to itself.
 * Note - TeleportLinks can only be instantiated by a builder the ensures the link has two distinct nodes.
 */
class TeleportLink implements Link {
    private NodePair nodePair;

    private TeleportLink() {}

    protected TeleportLink(NodePair nodePair) {
        this.nodePair = nodePair;
    }

    @Override
    public Node getOtherNode(Node node) {
        return null;
    }

    @Override
    public String toString() {
        return nodeOne + " -> " + nodeTwo + " (" + getLinkWeight() + ")";
    }
}
