package com.challenge.graph.router;

import com.challenge.graph.Graph;

/**
 * Simple factory for creating different types of routers.
 *
 * A router only takes the graph as a parameter so a simple create method is sufficient;
 * there is no need for additional builder style methods.
 */
public class RouterFactory {
    public enum RouterTypes {
        TELEPORTATION,
         // No other router types are supported at this time.
    }

    public static Router createRouter(RouterTypes type, Graph graph) {
        return switch (type) {
            case TELEPORTATION -> new TeleportationRouter(graph);
            default -> throw new IllegalArgumentException(String.format("Router type: %s is unsupported!", type));
        };
    }
}
