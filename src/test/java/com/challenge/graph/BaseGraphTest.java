package com.challenge.graph;

import com.challenge.graph.queries.CitiesFromQuery;
import com.challenge.graph.queries.LoopPossibleQuery;
import com.challenge.graph.queries.TeleportFromQuery;
import com.challenge.graph.utility.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
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
                "cities from Seattle in 1 jumps",
                "cities from Seattle in 2 jumps",
                "can I teleport from New York to Atlanta",
                "can I teleport from Oakland to Atlanta",
                "loop possible from Oakland",
                "loop possible from Washington"
        );
        baseParser.parseLines(inputStream);
    }

    @Test
    void testBaseSetup() {
        // Verify the base setup
        assertAll(() -> {
            assertEquals(9, baseParser.getLinks().size());
            assertTrue(baseParser.getLinks().contains(new Pair<>("Washington", "Baltimore")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("Washington", "Atlanta")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("Baltimore", "Philadelphia")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("Philadelphia", "New York")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("Los Angeles", "San Fransisco")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("San Fransisco", "Oakland")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("Los Angeles", "Oakland")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("Seattle", "New York")));
            assertTrue(baseParser.getLinks().contains(new Pair<>("Seattle", "Baltimore")));

            assertEquals(6, baseParser.getQueries().size());
            assertInstanceOf(CitiesFromQuery.class, baseParser.getQueries().get(0));
            assertEquals("Seattle", ((CitiesFromQuery) baseParser.getQueries().get(0)).getCity());
            assertEquals(1, ((CitiesFromQuery) baseParser.getQueries().get(0)).getMaxHops());

            assertInstanceOf(CitiesFromQuery.class, baseParser.getQueries().get(1));
            assertEquals("Seattle", ((CitiesFromQuery) baseParser.getQueries().get(1)).getCity());
            assertEquals(2, ((CitiesFromQuery) baseParser.getQueries().get(1)).getMaxHops());

            assertInstanceOf(TeleportFromQuery.class, baseParser.getQueries().get(2));
            assertEquals("New York", ((TeleportFromQuery) baseParser.getQueries().get(2)).getFromCity());
            assertEquals("Atlanta", ((TeleportFromQuery) baseParser.getQueries().get(2)).getToCity());

            assertInstanceOf(TeleportFromQuery.class, baseParser.getQueries().get(3));
            assertEquals("Oakland", ((TeleportFromQuery) baseParser.getQueries().get(3)).getFromCity());
            assertEquals("Atlanta", ((TeleportFromQuery) baseParser.getQueries().get(3)).getToCity());

            assertInstanceOf(LoopPossibleQuery.class, baseParser.getQueries().get(4));
            assertEquals("Oakland", ((LoopPossibleQuery) baseParser.getQueries().get(4)).getCity());

            assertInstanceOf(LoopPossibleQuery.class, baseParser.getQueries().get(5));
            assertEquals("Washington", ((LoopPossibleQuery) baseParser.getQueries().get(5)).getCity());
        });
    }
}
