package com.challenge.graph;

import java.util.Objects;
import java.util.Random;

/**
 * A node in a graph.
 *
 * When Node instances are compared, the ids are compared for equality.
 * (see the equals() and hashCode() methods)
 */
public class Node {
    private final int id;

    public Node() {
        this.id = generateNodeId();
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (!(otherObject instanceof Node otherNode)) return false;
        return this.id == otherNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // This method could be extracted to a default method in an interface which this Node class implements
    // TODO - Consider a more flexible approach, possibly include specifying a unique ID generator strategy
    private static Integer generateNodeId() {
        return new Random().nextInt(10000);
    }
}
