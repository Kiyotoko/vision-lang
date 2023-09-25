package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

@Immutable
public class BaseOperator implements Operator {

    public static final Operator ADD = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() + b.get().doubleValue()), "+", 1);
    public static final Operator SUBTRACT = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() - b.get().doubleValue()), "-", 1);
    public static final Operator MULTIPLY = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() * b.get().doubleValue()), "*", 2);
    public static final Operator DIVIDE = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() / b.get().doubleValue()), "/", 2);
    public static final Operator MOD = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() % b.get().doubleValue()), "%", 3);
    public static final Operator POW = new BaseOperator((a, b) ->
            new Constant(Math.pow(a.get().doubleValue(), b.get().doubleValue())), "^", 4);
    public static final Operator EQU = new BaseOperator((a, b) -> new Constant(a.get().equals(b.get()) ? 1 : 0),
            "=", 0);

    private final @Nonnull BiFunction<Value, Value, Value> function;
    private final @Nonnull String ident;
    private final int priority;
    private final boolean sign;

    public BaseOperator(@Nonnull BinaryOperator<Value> function, @Nonnull String ident, int priority) {
        this.function = function;
        this.ident = ident;
        this.priority = priority;
        this.sign = priority == 1;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public Value evaluate(@Nonnull Value left, @Nonnull Value right) {
        return function.apply(left, right);
    }

    @CheckReturnValue
    @Override
    public int priority() {
        return priority;
    }

    @CheckReturnValue
    @Override
    public boolean isSign() {
        return sign;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public String toString() {
        return ident;
    }
}
