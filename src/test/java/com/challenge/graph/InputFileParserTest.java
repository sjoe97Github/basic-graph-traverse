package com.challenge.graph;

import com.challenge.graph.queries.CitiesFromQuery;
import com.challenge.graph.queries.LoopPossibleQuery;
import com.challenge.graph.queries.TeleportFromQuery;
import com.challenge.graph.utility.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InputFileParserTest extends BaseGraphTest  {
    InputFileParser parser;

    @BeforeEach
    void setUp() {
        parser = new InputFileParser();
    }

    @Test
    void testParse_ValidInput() throws IOException {
        Stream<String> inputStream = Stream.of(
                "New York - London",
                "London - Paris",
                "cities from New York in 1 jumps",
                "can I teleport from New York to Paris",
                "loop possible from New York"
        );

        assertDoesNotThrow(() -> parser.parseLines(inputStream));

        // Verify the expected number of post-parse links and queries have been created and that;
        // the order of the generated queries matches the order or the corresponding strings in parsed stream.
        assertAll(() -> {
            assertEquals(2, parser.getLinks().size());
            assertTrue(parser.getLinks().contains(new Pair<>("New York", "London")));
            assertTrue(parser.getLinks().contains(new Pair<>("London", "Paris")));

            assertEquals(3, parser.getQueries().size());
            assertInstanceOf(CitiesFromQuery.class, parser.getQueries().get(0));
            assertInstanceOf(TeleportFromQuery.class, parser.getQueries().get(1));
            assertInstanceOf(LoopPossibleQuery.class, parser.getQueries().get(2));
        });

        // Verify that the CitiesFromQuery has been correctly constructed and that string values
        // have been converted to lower case.
        CitiesFromQuery citiesFromQuery = (CitiesFromQuery) parser.getQueries().get(0);
        assertAll(() -> {
            assertEquals("New York", citiesFromQuery.getCity());
            assertEquals(1, citiesFromQuery.getMaxHops());
        });
    }

    @Test
    void testParse_DuplicateLinks() throws IOException {
        Stream<String> inputStream = Stream.of(
                "New York - London",
                "New York - London",
                "London - Paris"
        );

        assertDoesNotThrow(() -> parser.parseLines(inputStream));

        assertAll(() -> {
            assertEquals(2, parser.getLinks().size());
            assertTrue(parser.getLinks().contains(new Pair<>("New York", "London")));
            assertTrue(parser.getLinks().contains(new Pair<>("London", "Paris")));
        });
    }

    @Test
    @Disabled("TODO - This test is not implemented yet")
    void testParse_InvalidInput() throws IOException {
        // TODO - Implement ...
        // Test the following edge cases:
        // 1. Invalid city to city route Pair format
        // 2. Extra spaces in city names
        // 3. Incorrectly formatted query strings of a variety of types?
        // 4. ???
    }
}