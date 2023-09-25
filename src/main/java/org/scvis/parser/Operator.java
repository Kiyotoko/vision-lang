package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public interface Operator extends Token, Comparable<Operator> {

    Operator SEPARATOR = new Operator() {
        @Nonnull
        @Override
        public Value evaluate(@Nonnull Value left, @Nonnull Value right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isSign() {
            return false;
        }

        @Override
        public int priority() {
            return -1;
        }
    };

    @CheckReturnValue
    @Nonnull
    Value evaluate(@Nonnull Value left, @Nonnull Value right);

    @CheckReturnValue
    boolean isSign();

    @CheckReturnValue
    int priority();

    @Override
    default int compareTo(@Nonnull Operator o) {
        return o.priority() - priority();
    }
}
