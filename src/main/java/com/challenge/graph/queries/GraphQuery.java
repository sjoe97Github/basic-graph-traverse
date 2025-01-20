package com.challenge.graph.queries;

import com.challenge.graph.Graph;

/**
 * The intent is that both execute() and getResult() should return the String result of executing the query.
 * This intent, then, allows users to either process the query results immediately and directly using the execute method,
 * or to retrieve the results later on using the getResult method.
 */
public interface GraphQuery {
    GraphQuery prepare();
    String execute(Graph graph);
    String getResult();
}
