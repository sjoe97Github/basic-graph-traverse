package com.challenge.graph.queries;

import com.challenge.graph.BaseGraphTest;
import com.challenge.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GraphQueryTest  extends BaseGraphTest {
    Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();

        // Populate the graph using the links provided in the input file.
        baseParser.getLinks().forEach(route -> {
            graph.addNodeRoute(route);
        });
    }

    @Test
    void testCitiesFromQuery() {
        GraphQuery query = new CitiesFromQuery("CITIES FROM Seattle in 2 jumps".toLowerCase()).prepare();
        var result = query.execute(graph);

        assertTrue(result.contains("jumps: "));
        assertTrue(result.contains(","));
        // Parse out the resulting cities into a set.
        // TODO - Overkill?
        var resultingTwoHopCities = Arrays.stream(result.split("jumps: ")[1].split(", ")).map(String::trim).collect(Collectors.toSet());
        assertAll(() -> {
            assertEquals(4, resultingTwoHopCities.size());
            assertTrue(resultingTwoHopCities.containsAll(Set.of("baltimore", "philadelphia", "washington", "new york")));
        });

        //
        // Test only one hop
        //
        query = new CitiesFromQuery("CITIES FROM Seattle in 1 jumps".toLowerCase()).prepare();
        result = query.execute(graph);

        assertTrue(result.contains("jumps: "));
        assertTrue(result.contains(","));
        // Parse out the resulting cities into a set.
        // TODO - Overkill?
        var resultingOneHopCities = Arrays.stream(result.split("jumps: ")[1].split(", ")).map(String::trim).collect(Collectors.toSet());
        assertAll(() -> {
            assertEquals(2, resultingOneHopCities.size());
            assertTrue(resultingOneHopCities.containsAll(Set.of("baltimore", "new york")));
        });

    }
}