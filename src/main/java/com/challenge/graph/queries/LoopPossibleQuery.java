package com.challenge.graph.queries;

import com.challenge.graph.Graph;

public class LoopPossibleQuery implements GraphQuery {
    private static final String RESULT_PREFIX_TEMPLATE = "loop possible from %s: %s";

    // Protected so accessible to test classes
    private final String rawQuery;
    protected String city;
    protected String loopPossible = "no";
    protected String result = "";

    public LoopPossibleQuery(String rawQuery) {
        this.rawQuery = rawQuery;
    }

    @Override public GraphQuery prepare() {
        // the raw query string should be in the form: "loop possible from TheCity"
        String[] lineParts = rawQuery.split("from");
        if (lineParts.length == 2) {
            this.city = lineParts[1].trim();
        } else {
            throw new IllegalArgumentException("Invalid 'loop possible' input line format: " + rawQuery);
        }
        return this;
    }

    @Override
    public String execute(Graph graph) {
        this.loopPossible = graph.isLoopBackPossible(this.city) ? "yes" : "no";

        // Construct and return the result string
        return this.result = String.format(RESULT_PREFIX_TEMPLATE, this.city, this.loopPossible);
    }

    @Override
    public String getResult() {
        return this.result;
    }

    public String getLoopPossible() {
        return loopPossible;
    }

    public String getCity() {
        return city;
    }
}
