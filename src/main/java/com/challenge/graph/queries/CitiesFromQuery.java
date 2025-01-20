package com.challenge.graph.queries;

import com.challenge.graph.Graph;
import com.challenge.graph.StringNode;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CitiesFromQuery implements GraphQuery {
    // TODO - Consider if this should be passed into a constructor or a setter?
    private static final String RESULT_PREFIX_TEMPLATE = "cities from %s in %s jumps: ";

    // Protected so accessible to test classes
    private final String rawQuery;
    protected String city;
    protected Integer maxHops;
    protected Set<String> reachableCities = new HashSet<>();
    protected String result = "";

    public CitiesFromQuery(String rawQuery) {
        this.rawQuery = rawQuery;
    }


    @Override public GraphQuery prepare() {
        // the raw query string should be in the form: "cities from TheCity in N jumps"
        Pattern pattern = Pattern.compile("from\\s+(.+?)\\s+in\\s+(.+?)\\s+jumps");
        Matcher matcher = pattern.matcher(rawQuery);
        if (matcher.find()) {
            this.city = matcher.group(1);
            if (this.city == null || this.city.isEmpty()) {
                throw new IllegalArgumentException("Invalid city name in query: " + rawQuery);
            }

            this.city = this.city.trim(); // TODO - Should not need trim due to regex group matching
            String jumpsValue = matcher.group(2);
            try {
                this.maxHops = Integer.parseInt(jumpsValue);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unable to parse jumps value into an Integer, query: " + rawQuery);
            }
        }

        return this;
    }

    @Override
    public String execute(Graph graph) {
        this.reachableCities = graph.reachableNodes(this.city, this.maxHops)
                                    .stream()
                                    .map(StringNode::getName)
                                    .collect(Collectors.toSet());

        // Construct and return the result string
        return this.result = String.format(RESULT_PREFIX_TEMPLATE, this.city, this.maxHops) +
                       this.reachableCities.stream().collect(Collectors.joining(", "));
    }

    @Override
    public String getResult() {
        return this.result;
    }

    public String getCity() {
        return city;
    }

    public Integer getMaxHops() {
        return maxHops;
    }
}
