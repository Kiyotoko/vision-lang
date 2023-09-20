package org.scvis.parser;

import org.scvis.math.Stochastic;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function implements Value {

    private final @Nonnull Number value;

    public static final @Nonnull Map<String, Evaluator> EVALUATOR_MAP = new HashMap<>();
    static  {
         EVALUATOR_MAP.putAll(Map.of(
                "sin", a -> Math.sin(a[0].doubleValue()), "cos", a -> Math.cos(a[0].doubleValue()),
                "tan", a -> Math.tan(a[0].doubleValue()), "asin", a -> Math.asin(a[0].doubleValue()),
                "acos", a -> Math.acos(a[0].doubleValue()), "atan", a -> Math.atan(a[0].doubleValue()),
                "sqrt", a -> Math.sqrt(a[0].doubleValue()), "abs", a -> Math.abs(a[0].doubleValue()),
                "signum", a -> Math.signum(a[0].doubleValue()), "hypot", a -> Math.hypot(a[0].doubleValue(),
                        a[1].doubleValue())
         ));
         EVALUATOR_MAP.putAll(Map.of(
                 "fac", args -> Stochastic.factorial(args[0].intValue()),
                 "binom", args -> Stochastic.binom(args[0].intValue(), args[1].intValue()),
                 "bernoullipdf", args -> Stochastic.bernoulliPdf(args[0].doubleValue(), args[1].intValue(),
                         args[2].intValue()),
                 "bernoullicdf", args -> Stochastic.bernoulliCdf(args[0].doubleValue(), args[1].intValue(),
                         args[2].intValue(), args[3].intValue()),
                 "minimalattempts", args -> Stochastic.minimalAttempts(args[0].doubleValue(), args[1].doubleValue(),
                         args[2].intValue())
         ));
    }

    public Function(@Nonnull String name, @Nonnull List<Operator> operators, @Nonnull List<Object> tokens) throws NoSuchMethodException {
        Evaluator evaluator = EVALUATOR_MAP.get(name.toLowerCase());
        if (evaluator == null)
            throw new NoSuchMethodException("No method found for " + name);
        List<Number> values = new TokenEvaluator(operators, tokens).evaluate();
        value = evaluator.evaluate(values.toArray(new Number[0]));
    }

    @FunctionalInterface
    public interface Evaluator {
        Number evaluate(Number... args);
    }

    @Nonnull
    @Override
    public Number get() {
        return value;
    }
}
