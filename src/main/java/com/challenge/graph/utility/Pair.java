package com.challenge.graph.utility;

import java.util.Objects;

public class Pair<A, B> {
    private final A valueA;
    private final B valueB;

    public Pair(A valueA, B valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    public A getValueA() { return valueA; }
    public B getValueB() { return valueB; }
    
     @Override
     public int hashCode() {
         // TODO - Investigate the Objects.hash() method.
         return Objects.hash(valueA, valueB);
     }
    
     @Override
     public boolean equals(Object obj) {
         if (this == obj) return true;
         if (!(obj instanceof Pair<?, ?> other)) return false;
         return Objects.equals(valueA, other.valueA) && Objects.equals(valueB, other.valueB);
     }
}