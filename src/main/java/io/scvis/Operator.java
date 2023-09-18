package io.scvis;

import javax.annotation.Nonnull;

public interface Operator extends Comparable<Operator> {

    @Nonnull Value evaluate(@Nonnull Value left, @Nonnull Value right);

    default boolean zeroAsLeft() {
        return false;
    }

    int priority();

    @Override
    default int compareTo(@Nonnull Operator o) {
        return o.priority() - priority();
    }

}
