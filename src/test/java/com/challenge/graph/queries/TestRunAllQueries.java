package com.challenge.graph.queries;

import com.challenge.graph.Graph;
import com.challenge.graph.GraphBuilder;
import com.challenge.graph.InputFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is really a functional rather than unit test because of the following steps taken in this test:
 * 1. Create a parser from a specified test input file.
 * 2. Create a collection of expected output lines from a specified test output file.
 * 3. Run all queries from the parser and match with the expected output lines.
 */
public class TestRunAllQueries {
    private static final String TEST_INPUT_FILE = "testinput.txt";
    private static final String TEST_OUTPUT_FILE = "testoutput.txt";

    InputFileParser parser;
    LinkedList<String> expectedOutputList = new LinkedList<>();

    @BeforeEach
    void setup() throws URISyntaxException {
        setupInputFileParserWithTestData();
        setupExpectedOutputList();
        assertEquals(6, expectedOutputList.size());
    }

    private void setupInputFileParserWithTestData() {
        final ClassLoader[] classLoader = new ClassLoader[1];   // So I can use it in the assertDoesNotThrow lambdas below
        assertDoesNotThrow(() -> {
            classLoader[0] = getClass().getClassLoader();
        });

        final File[] inputFile = new File[1];    // So I can use it in the following assertDoesNotThrow lambda
        assertDoesNotThrow(() -> {
            URL inputFileResoure = classLoader[0].getResource(TEST_INPUT_FILE);
            assertNotNull(inputFileResoure);

            inputFile[0] = new File(inputFileResoure.getFile());
            assertTrue(inputFile[0].exists());
        });

        parser = new InputFileParser();
        parser.parse(inputFile[0]);

        assertEquals(9, parser.getLinks().size());
        assertEquals(6, parser.getQueries().size());
    }

    private void setupExpectedOutputList() {
        final ClassLoader[] classLoader = new ClassLoader[1];   // So I can use it in the assertDoesNotThrow lambdas below
        assertDoesNotThrow(() -> {
            classLoader[0] = getClass().getClassLoader();
        });

        final File[] expectedOutputFile = new File[1];    // So I can use it in the following assertDoesNotThrow lambda
        assertDoesNotThrow(() -> {
            URL expectedOutputFileResoure = classLoader[0].getResource(TEST_OUTPUT_FILE);
            assertNotNull(expectedOutputFileResoure);

            expectedOutputFile[0] = new File(expectedOutputFileResoure.getFile());
            assertTrue(expectedOutputFile[0].exists());
        });

        assertDoesNotThrow(() -> populatedExpectedOutputList(expectedOutputFile[0]));
    }

    private void populatedExpectedOutputList(File expectedOutputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(expectedOutputFile))) {
            String line;
            while ((line = br.readLine())!= null) {
                this.expectedOutputList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading expected output file: " + e.getMessage(), e);
        }
    }

    @Test
    void testRunAllQueries() {
        GraphBuilder builder = new GraphBuilder();
        builder.addNodeRoutes(parser.getLinks());
        Graph graph = builder.build();

        LinkedList<String> parsedOutput = parser.getQueries().stream()
                                            .map(query -> query.prepare().execute(graph))
                                            .collect(Collectors.toCollection(LinkedList::new));

        assertEquals(expectedOutputList, parsedOutput);
        assertTrue(true);
    }
}
