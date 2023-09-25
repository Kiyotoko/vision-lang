package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

public interface Value extends Token {

    @CheckReturnValue
    @Nonnull
    Number get();
}
