package org.scvis.parser;

import org.scvis.math.Stochastic;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function implements Value {

    private final @Nonnull Number value;

    protected static final @Nonnull Map<String, Evaluator> EVALUATOR_MAP = new HashMap<>();
    static  {
         EVALUATOR_MAP.putAll(Map.of(
                 "sin", checked(a -> Math.sin(a[0].doubleValue()), 1),
                 "cos", checked(a -> Math.cos(a[0].doubleValue()), 1),
                 "tan", checked(a -> Math.tan(a[0].doubleValue()), 1),
                 "asin", checked(a -> Math.asin(a[0].doubleValue()), 1),
                 "acos", checked(a -> Math.acos(a[0].doubleValue()), 1),
                 "atan", checked(a -> Math.atan(a[0].doubleValue()), 1),
                 "sqrt", checked(a -> Math.sqrt(a[0].doubleValue()), 1),
                 "abs", checked(a -> Math.abs(a[0].doubleValue()), 1),
                 "signum", checked(a -> Math.signum(a[0].doubleValue()), 1),
                 "hypot", checked(a -> Math.hypot(a[0].doubleValue(), a[1].doubleValue()), 2)
         ));
         EVALUATOR_MAP.putAll(Map.of(
                 "fac", checked(a -> Stochastic.factorial(a[0].intValue()), 1),
                 "binom", checked(a -> Stochastic.binom(a[0].intValue(), a[1].intValue()), 2),
                 "bernoulli_pdf", checked(a -> Stochastic.bernoulliPdf(a[0].doubleValue(), a[1].intValue(),
                         a[2].intValue()), 3),
                 "bernoulli_cdf", checked(a -> Stochastic.bernoulliCdf(a[0].doubleValue(), a[1].intValue(),
                         a[2].intValue(), a[3].intValue()), 4),
                 "minimal_attempts", checked(a -> Stochastic.minimalAttempts(a[0].doubleValue(),
                         a[1].doubleValue(), a[2].intValue()), 3)
         ));
    }

    public Function(@Nonnull String name, @Nonnull List<Operator> operators, @Nonnull List<Token> tokens) {
        Evaluator evaluator = EVALUATOR_MAP.get(name.toLowerCase());
        if (evaluator == null)
            throw new EvaluationException("No method found for " + name);
        List<Number> values = new TokenEvaluator(operators, tokens).evaluate();
        value = evaluator.evaluate(values.toArray(new Number[0]));
    }

    @FunctionalInterface
    public interface Evaluator {
        Number evaluate(Number... args);
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
    public Number get() {
        return value;
    }
}
