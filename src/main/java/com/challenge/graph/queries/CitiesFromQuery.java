package com.challenge.graph.queries;

import com.challenge.graph.Graph;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CitiesFromQuery implements GraphQuery {
    // Protected so accessible to test classes
    private final String rawQuery;
    protected String city;
    protected Integer maxHops;

    protected Set<String> reachableCities = new HashSet<>();

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

            this.city = this.city.toLowerCase();
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
        return "";
    }

    @Override
    public String getResult() {
        return "";
    }

    public String getCity() {
        return city;
    }

    public Integer getMaxHops() {
        return maxHops;
    }
}
