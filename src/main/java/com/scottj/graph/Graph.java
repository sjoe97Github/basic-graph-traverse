package com.scottj.graph;

import com.scottj.graph.interfaces.Link;
import com.scottj.graph.interfaces.Node;

import java.util.*;

public class Graph {
    private final Map<Node, List<Link>> adjacencyList = new HashMap<>();

    private Graph(Map<Node, List<Link>> adjacencyList) {
        this.adjacencyList.putAll(adjacencyList);
    }

    public Node findNodeByName(String name) {
        return adjacencyList.keySet().stream()
                .filter(node -> node.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean canReachWithinHops(Node start, Node target, int hops) {
        if (hops < 0) return false;
        if (start.equals(target)) return true;

        for (Link link : adjacencyList.getOrDefault(start, Collections.emptyList())) {
            if (canReachWithinHops(link.getOtherNode(start), target, hops - 1)) {
                return true;
            }
        }
        return false;
    }

    public Set<Node> findAllReachableNodes(Node start) {
        Set<Node> visited = new HashSet<>();
        exploreNodes(start, visited);
        return visited;
    }

    private void exploreNodes(Node current, Set<Node> visited) {
        if (visited.contains(current)) return;
        visited.add(current);

        for (Link link : adjacencyList.getOrDefault(current, Collections.emptyList())) {
            exploreNodes(link.getOtherNode(current), visited);
        }
    }

    public boolean hasTwoWayRoute(Node start, Node target) {
        Set<Node> visited = new HashSet<>();
        return canReach(start, target, visited) && canReach(target, start, new HashSet<>());
    }

    private boolean canReach(Node current, Node target, Set<Node> visited) {
        if (current.equals(target)) return true;
        if (visited.contains(current)) return false;
        visited.add(current);

        for (Link link : adjacencyList.getOrDefault(current, Collections.emptyList())) {
            if (canReach(link.getOtherNode(current), target, visited)) {
                return true;
            }
        }
        return false;
    }

    public List<Link> getLinks(Node node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public Set<Node> getNodes() {
        return adjacencyList.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : adjacencyList.keySet()) {
            sb.append(node).append(": ").append(adjacencyList.get(node)).append("\n");
        }
        return sb.toString();
    }
}

