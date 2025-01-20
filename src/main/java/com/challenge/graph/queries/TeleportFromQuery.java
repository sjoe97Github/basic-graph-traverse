package com.challenge.graph.queries;

import com.challenge.graph.Graph;

public class TeleportFromQuery implements GraphQuery {
    // Protected so accessible to test classes
    private final String rawQuery;
    protected String fromCity;
    protected String toCity;
    protected String teleportPossible = "no";

    public TeleportFromQuery(String rawQuery) {
        this.rawQuery = rawQuery;
    }

    @Override public GraphQuery prepare() {
        // the raw query string should be in the form: "can I teleport from TheCity to TheOtherCity"
        String[] lineParts = rawQuery.split("to");
        if (lineParts.length == 2) {
            // The SECOND part (the fragment to the right of the 'to' separator) should be the value of "TheOtherCity"
            this.toCity = lineParts[1].trim().toLowerCase();

            // The FIRST part (the fragment to the left of the 'to' separator) should be of the form "can I teleport from TheCity"
            // Split the left portion around the 'from' separator to get the value of from city (the starting city)
            String[] firstPartParts = lineParts[0].split("from");
            if (firstPartParts.length == 2) {
                this.fromCity = firstPartParts[1].trim().toLowerCase();
            } else {
                throw new IllegalArgumentException("Invalid 'can I teleport from TheCity to TheOtherCity' input line format: " + rawQuery);
            }
        } else {
            throw new IllegalArgumentException("Invalid 'can I teleport from TheCity to TheOtherCity' input line format: " + rawQuery);
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

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public String getTeleportPossible() {
        return teleportPossible;
    }
}
