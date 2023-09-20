package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public interface Value {

    @CheckReturnValue
    @Nonnull
    Number get();
}
