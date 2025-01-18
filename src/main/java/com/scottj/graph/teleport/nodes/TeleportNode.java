package com.scottj.graph.teleport.nodes;

import com.scottj.graph.interfaces.Node;

public class TeleportNode implements Node {
    private final Integer nodeId = generateNodeId();
    private String name;

    // protected scope restricts construction to subclasses, builders, and unit tests.
    protected TeleportNode() {}

    public TeleportNode(String name) {
        this.name = name;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //  Note - This technique is known as pattern matching instance of
        if (!(o instanceof TeleportNode graphNode)) return false;
        return nodeId.equals(graphNode.getNodeId());
    }

    @Override
    public int hashCode() {
        return nodeId.hashCode();
    }
}
