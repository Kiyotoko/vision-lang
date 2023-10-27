package org.scvis.parser;

import org.scvis.math.Stochastic;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function implements Value<Object> {

    private final @Nonnull Object value;

    protected static final @Nonnull Map<String, Evaluator> EVALUATOR_MAP = new HashMap<>();

    static {
        EVALUATOR_MAP.putAll(Map.of(
                "sin", checked(a -> Math.sin(a[0].getDouble()), 1),
                "cos", checked(a -> Math.cos(a[0].getDouble()), 1),
                "tan", checked(a -> Math.tan(a[0].getDouble()), 1),
                "asin", checked(a -> Math.asin(a[0].getDouble()), 1),
                "acos", checked(a -> Math.acos(a[0].getDouble()), 1),
                "atan", checked(a -> Math.atan(a[0].getDouble()), 1),
                "sqrt", checked(a -> Math.sqrt(a[0].getDouble()), 1),
                "abs", checked(a -> Math.abs(a[0].getDouble()), 1),
                "signum", checked(a -> Math.signum(a[0].getDouble()), 1),
                "hypot", checked(a -> Math.hypot(a[0].getDouble(), a[1].getDouble()), 2)
        ));
        EVALUATOR_MAP.putAll(Map.of(
                "fac", checked(a -> Stochastic.factorial(a[0].getInteger()), 1),
                "binom", checked(a -> Stochastic.binom(a[0].getInteger(), a[1].getInteger()), 2),
                "bernoulli_pdf", checked(a -> Stochastic.bernoulliPdf(a[0].getDouble(), a[1].getInteger(),
                        a[2].getInteger()), 3),
                "bernoulli_cdf", checked(a -> Stochastic.bernoulliCdf(a[0].getDouble(), a[1].getInteger(),
                        a[2].getInteger(), a[3].getInteger()), 4),
                "minimal_attempts", checked(a -> Stochastic.minimalAttempts(a[0].getDouble(),
                        a[1].getDouble(), a[2].getInteger()), 3)
        ));
    }

    public Function(@Nonnull String name, @Nonnull List<Operator> operators, @Nonnull List<Token> tokens) {
        Evaluator evaluator = EVALUATOR_MAP.get(name.toLowerCase());
        if (evaluator == null)
            throw new EvaluationException("No method found for " + name);
        List<Value<?>> values = new TokenEvaluator(operators, tokens).evaluate();
        value = evaluator.evaluate(values.toArray(new Value[0]));
    }

    @FunctionalInterface
    public interface Evaluator {
        Object evaluate(Value<?>... args);
    }

    public static Evaluator checked(Evaluator evaluator, int count) {
        return args -> {
            if (count != args.length)
                throw new EvaluationException("Expected " + count + " arguments, got " + args.length);
            return evaluator.evaluate(args);
        };
    }

    @Nonnull
    @Override
    public Object get() {
        return value;
    }
}
