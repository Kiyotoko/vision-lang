package org.scvis.parser;

public interface Token {

    default boolean isOperator() {
        return this instanceof Operator;
    }

    default boolean isValue() {
        return this instanceof Value;
    }
}
