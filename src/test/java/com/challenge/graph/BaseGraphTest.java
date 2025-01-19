package com.challenge.graph;

import com.challenge.graph.queries.CitiesFromQuery;
import com.challenge.graph.queries.LoopPossibleQuery;
import com.challenge.graph.queries.TeleportFromQuery;
import com.challenge.graph.utility.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BaseGraphTest {
    protected InputFileParser baseParser;

    @BeforeEach
    void setUpBaseTest() {
        baseParser = new InputFileParser();
        Stream<String> inputStream = Stream.of(
                "Washington - Baltimore",
                "Washington - Atlanta",
                "Baltimore - Philadelphia",
                "Philadelphia - New York",
                "Los Angeles - San Fransisco",
                "San Fransisco - Oakland",
                "Los Angeles - Oakland",
                "Seattle - New York",
                "Seattle - Baltimore",
                "CITIES FROM Seattle in 1 jumps",
                "CITIES FROM Seattle in 2 jumps",
                "CAN I TELEPORT FROM New York TO Atlanta",
                "CAN I TELEPORT FROM Oakland TO Atlanta",
                "LOOP POSSIBLE FROM Oakland",
                "LOOP POSSIBLE FROM Washington"
        );
        baseParser.parseLines(inputStream);
    }

    @Test
    void testBaseSetup() {
        // Verify the base setup
        assertAll(() -> {
            assertEquals(9, baseParser.getLinks().size());
            assertTrue(baseParser.getLinks().contains(new Pair<>("washington", "baltimore")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("washington", "atlanta")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("baltimore", "philadelphia")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("philadelphia", "new york")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("los angeles", "san fransisco")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("san fransisco", "oakland")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("los angeles", "oakland")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("seattle", "new york")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("seattle", "baltimore")));

            assertEquals(6, baseParser.getQueries().size());
            assertInstanceOf(CitiesFromQuery.class, baseParser.getQueries().get(0));
            assertEquals("seattle", ((CitiesFromQuery) baseParser.getQueries().get(0)).getCity());
            assertEquals(1, ((CitiesFromQuery) baseParser.getQueries().get(0)).getMaxHops());

            assertInstanceOf(CitiesFromQuery.class, baseParser.getQueries().get(1));
            assertEquals("seattle", ((CitiesFromQuery) baseParser.getQueries().get(1)).getCity());
            assertEquals(2, ((CitiesFromQuery) baseParser.getQueries().get(1)).getMaxHops());

            assertInstanceOf(TeleportFromQuery.class, baseParser.getQueries().get(2));
            assertEquals("new york", ((TeleportFromQuery) baseParser.getQueries().get(2)).getFromCity());
            assertEquals("atlanta", ((TeleportFromQuery) baseParser.getQueries().get(2)).getToCity());

            assertInstanceOf(TeleportFromQuery.class, baseParser.getQueries().get(3));
            assertEquals("oakland", ((TeleportFromQuery) baseParser.getQueries().get(3)).getFromCity());
            assertEquals("atlanta", ((TeleportFromQuery) baseParser.getQueries().get(3)).getToCity());

            assertInstanceOf(LoopPossibleQuery.class, baseParser.getQueries().get(4));
            assertEquals("oakland", ((LoopPossibleQuery) baseParser.getQueries().get(4)).getCity());

            assertInstanceOf(LoopPossibleQuery.class, baseParser.getQueries().get(5));
            assertEquals("washington", ((LoopPossibleQuery) baseParser.getQueries().get(5)).getCity());
        });
    }
}
