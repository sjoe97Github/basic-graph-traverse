package com.challenge.graph;

import com.challenge.graph.router.Route;

import java.util.HashSet;
import java.util.Set;

public class TeleportationService {
    public Set<String> whatCitiesAreReachableFrom(String originCity, int maxJumps) {
        return new HashSet<>(); // Placeholder for actual implementation
    }

    /**
     * Which cities are reachable from a specified origin city, return the first
     * available route between them.
     *
     * @param originCity - name of the city from which to start the search.
     * @param maxJumps - maximum number of jumps allowed during the search.
     * @return Route - null if no route; otherwise the first discovered route between the cities.
     */
    public Set<String> citiesReachableFrom(String originCity, int maxJumps) {
        return null; // Placeholder for actual implementation
    }

    /**
     * Is a specified destination city is reachable from a specified origin city, return the first
     * available route between them.
     *
     * @param originCity - name of the city from which to start the search.
     * @param destinationCity - name of the city to reach.
     * @return Route - null if no route; otherwise the first discovered route between the cities.
     */
    public Boolean isCityReachableFrom(String originCity, String destinationCity) {
        return null; // Placeholder for actual implementation
    }

    /**
     * Determines if it's possible to loop back to the starting city without traversing and previously visited cities.
     *
     * @param originCity the name of the city from which to start the search.
     * @return Route - null if no loop; otherwise a route representing the first discovered loop.
     * @apiNote A loop does not allow return to the origin city by any portion of the destination route.
     */
    public Boolean isLoopBackPossible(String originCity) {
        return null; // Placeholder for actual implementation
    }
}
