package com.challenge.graph.queries;

import com.challenge.graph.Graph;

import java.util.HashSet;
import java.util.Set;

public class LoopPossibleQuery implements GraphQuery {
    // Protected so accessible to test classes
    private final String rawQuery;
    protected String city;

    protected String loopPossible = "no";

    public LoopPossibleQuery(String rawQuery) {
        this.rawQuery = rawQuery;
    }

    @Override public GraphQuery prepare() {
        // the raw query string should be in the form: "loop possible from TheCity"
        String[] lineParts = rawQuery.split("from");
        if (lineParts.length == 2) {
            this.city = lineParts[1].stripTrailing().stripLeading(); // TODO - should only need to strip leading whitespace
            this.city = this.city.toLowerCase();
        } else {
            throw new IllegalArgumentException("Invalid 'loop possible' input line format: " + rawQuery);
        }
        return this;
    }

    @Override
    public String execute(Graph graph) {
        return "";
    }

    @Override
    public String getResult() {
        return "";
    }

    public String getLoopPossible() {
        return loopPossible;
    }

    public String getCity() {
        return city;
    }
}
