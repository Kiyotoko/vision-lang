package io.scvis;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.DoubleFunction;

public class Function implements Value {

    private final @Nonnull Number value;

    public static final @Nonnull Map<String, DoubleFunction<Number>> DOUBLE_FUNCTION_MAP = Map.of(
            "sin", Math::sin, "cos", Math::cos, "tan", Math::tan,
            "asin", Math::asin, "acos", Math::acos, "atan", Math::atan,
            "sqrt", Math::sqrt, "abs", Math::abs, "signum", Math::signum
    );

    public Function(@Nonnull String name, @Nonnull Brackets brackets) throws NoSuchMethodException {
        DoubleFunction<Number> function = DOUBLE_FUNCTION_MAP.get(name);
        if (function == null)
            throw new NoSuchMethodException("No method found for " + name);
        value = function.apply(brackets.get().doubleValue());
    }

    @Nonnull
    @Override
    public Number get() {
        return value;
    }
}
