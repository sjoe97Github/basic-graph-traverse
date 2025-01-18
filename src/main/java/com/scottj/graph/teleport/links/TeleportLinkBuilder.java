package com.scottj.graph.teleport.links;

import com.scottj.graph.NodePair;
import com.scottj.graph.teleport.nodes.TeleportNode;

public class TeleportLinkBuilder {
    private TeleportNode firstNode;
    private TeleportNode otherNode;

    public TeleportLinkBuilder(TeleportNode firstNode) {
        if (firstNode == null) {
            throw new IllegalArgumentException("Builder cannot be instantiated with a NULL node.");
        }
        this.otherNode = firstNode;
    }

    public TeleportLinkBuilder setOtherNode(TeleportNode otherNode) {
        if (otherNode == null) {
            throw new IllegalArgumentException("Not allowed to link to a NULL node.");
        } else if (otherNode.equals(firstNode)) {
            throw new IllegalArgumentException("A node cannot be linked to itself.");
        }

        this.otherNode = otherNode;
        return this;
    }

    public TeleportLink build() {
        // The only additional validation required is to verify that the other node has been set.
        if (otherNode == null) {
            throw new IllegalStateException("The other node is required to build the link!");
        }

        return new TeleportLink(new NodePair(firstNode, otherNode));
    }
}
