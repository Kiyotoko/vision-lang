package org.scvis.parser;

import javax.annotation.Nonnull;
import java.util.List;

public class Brackets implements Value {

    private final @Nonnull Number value;

    public Brackets(@Nonnull List<Operator> operators, @Nonnull List<Token> tokens) {
        List<Number> values = new TokenEvaluator(operators, tokens).evaluate();
        if (values.size() != 1) {
            throw new EvaluationException("Brackets wrap one effective value, got " + values.size());
        }
        this.value = values.get(0);
    }

    @Nonnull
    @Override
    public Number get() {
        return value;
    }
}
