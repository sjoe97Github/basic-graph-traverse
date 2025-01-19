package com.challenge.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Node {
    private final int id;
    private final String name;
    private final Set<Node> connections;

    public Node(String name) {
        this.id = generateNodeId();
        this.name = name;
        this.connections = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Node> getConnections() {
        return connections;
    }

    // Only the GraphBuilder can link nodes together.
    // Note that being a method protected means it can also be accessed from unit tests classes
    protected void link(Node node) {
        connections.add(node);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        // Using Pattern matching instanceof accomplishes multiple things:
        // 1. It effectively excludes null values because null values are not an instance of any class.
        // 2. It verifies that the otherObject is an instance of this Node class.
        // 3. It casts otherObject to a Node instance so it can be used later as a Node instance.
        if (!(otherObject instanceof Node otherNode)) return false;
        return id == otherNode.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }

    // This method could be extracted to a default method in an interface which this Node class implements
    // TODO - Consider a more flexible approach, possibly include specifying a unique ID generator strategy
    private static Integer generateNodeId() {
        return new Random().nextInt(10000);
    }
}
