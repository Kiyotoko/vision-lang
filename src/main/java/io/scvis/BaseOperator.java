package io.scvis;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class BaseOperator implements Operator {

    public static final Operator ADD = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() + b.get().doubleValue()), 1) {
        @Override
        public boolean zeroAsLeft() {
            return true;
        }
    };
    public static final Operator SUBTRACT = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() - b.get().doubleValue()), 1) {
        @Override
        public boolean zeroAsLeft() {
            return true;
        }
    };
    public static final Operator MULTIPLY = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() * b.get().doubleValue()), 2);
    public static final Operator DIVIDE = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() / b.get().doubleValue()), 2);
    public static final Operator MOD = new BaseOperator((a, b) ->
            new Constant(a.get().doubleValue() % b.get().doubleValue()), 3);
    public static final Operator POW = new BaseOperator((a, b) ->
            new Constant(Math.pow(a.get().doubleValue(), b.get().doubleValue())), 4);

    private final @Nonnull BiFunction<Value, Value, Value> function;

    private final int priority;

    public BaseOperator(@Nonnull BinaryOperator<Value> function, int priority) {
        this.function = function;
        this.priority = priority;
    }

    @Nonnull
    @Override
    public Value evaluate(@Nonnull Value left, @Nonnull Value right) {
        return function.apply(left, right);
    }

    @Override
    public int priority() {
        return priority;
    }
}
