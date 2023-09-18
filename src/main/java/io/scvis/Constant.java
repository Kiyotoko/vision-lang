package io.scvis;

import javax.annotation.Nonnull;
import java.util.Map;

public class Constant implements Value {
    private final @Nonnull Number value;

    public static final Map<String, Constant> CONSTANT_MAP = Map.of(
            "e", new Constant(Math.E), "pi", new Constant(Math.PI));

    public static final Constant ZERO = new Constant(0.0);

    public Constant(@Nonnull Number value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public Number get() {
        return value;
    }
}
