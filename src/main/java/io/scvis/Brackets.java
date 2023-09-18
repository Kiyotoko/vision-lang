package io.scvis;

import javax.annotation.Nonnull;
import java.util.List;

public class Brackets implements Value {

    private final @Nonnull Number value;

    public Brackets(@Nonnull List<Operator> operators, @Nonnull List<Object> tokens) {
        value = new TokenEvaluator(operators, tokens).evaluate();
    }

    @Nonnull
    @Override
    public Number get() {
        return value;
    }
}
