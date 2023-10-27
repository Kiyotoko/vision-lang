package org.scvis.parser;

/**
 * A token has a specified function in an expression.
 *
 * @author karlz
 */
public interface Token {

    /**
     * Checks if this token is a value.
     *
     * @return <code>true</code> if this is a value, <code>false</code> otherwise
     */
    default boolean isValue() {
        return this instanceof Value;
    }
}
