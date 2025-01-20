package com.challenge.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * A node in a graph.

 * A factory or builder is required to create  string nodes, hence the private and protected constructors. As of this writing,
 * the only Node builder is the {@link com.challenge.graph.Graph} class. In the current design the graph class is solely
 * responsible for creating nodes and establishing links between them.
 *  @see com.challenge.graph.Graph#addNode(String)
 *  @see com.challenge.graph.Graph#addNodeRoute(com.challenge.graph.utility.Pair)
 *
 * Nodes have links to other nodes and these links are bidirectional. The Router associated with a Graph instance
 * uses these links to determine routes between nodes or otherwise traverse the graph.
 *
 * Note - Class and method links are IntelliJ-specific rather than standard, JavaDoc compliant links.
 */
public class StringNode extends Node {
    private String name;
    private Set<StringNode> nodeLinks;

    private StringNode() {
    }

    protected StringNode(String name) {
        super();
        this.name = name;
        this.nodeLinks = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    // Only the GraphBuilder can link nodes together.
    // Note that being a method protected means it can also be accessed from unit tests classes
    protected void link(StringNode node) {
        if (this == node) {
            throw new IllegalArgumentException("Can't link a node to itself.");
        }
        nodeLinks.add(node);
    }

    public Set<StringNode> getNodeLinks() {
        return nodeLinks;
    }

    public boolean isLinkedTo(StringNode node) {
        return nodeLinks.contains(node);
    }

    public boolean isLinkedTo(String nodeName) {
        return getNode(nodeName)!= null;
    }

    private StringNode getNode(String nodeName) {
        return nodeLinks.stream().filter(node -> node.getName().equalsIgnoreCase(nodeName)).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        // Using Pattern matching instanceof accomplishes multiple things:
        // 1. It effectively excludes null values because null values are not an instance of any class.
        // 2. It verifies that the otherObject is an instance of this Node class.
        // 3. It casts otherObject to a Node instance so it can be used later as a Node instance.
        if (!(otherObject instanceof StringNode otherNode)) return false;
        return this.name.equalsIgnoreCase(otherNode.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name.toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }
}
