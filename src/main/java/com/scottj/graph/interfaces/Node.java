package com.scottj.graph.interfaces;

import java.util.Random;

public interface Node {
    Integer getNodeId();
    String getName();

    default Integer generateNodeId() {
        return new Random().nextInt(10000);
    }
}