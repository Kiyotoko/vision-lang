package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.*;

public class TokenEvaluator {

    private final @Nonnull List<Operator> operators;
    private final @Nonnull List<Object> tokens;

    public TokenEvaluator(@Nonnull List<Operator> operators, @Nonnull List<Object> tokens) {
        this.operators = operators;
        Collections.sort(operators);
        this.tokens = tokens;
    }

    private void eliminate() {
        for (Operator operator : operators) {
            if (operator == Operator.SEPARATOR) break;
            int index = tokens.indexOf(operator);
            if (index == 0) {
                if (operator.isSign()) {
                    Value evaluated = operator.evaluate(Constant.ZERO, (Value) tokens.get(1));
                    tokens.remove(0);
                    tokens.set(0, evaluated);
                } else {
                    throw new UnsupportedOperationException("Operator does not allow zero as left!");
                }
            } else {
                Value evaluated = operator.evaluate((Value) tokens.get(index - 1), (Value) tokens.get(index + 1));
                tokens.remove(index + 1);
                tokens.remove(index);
                tokens.set(index - 1, evaluated);
            }
        }
        operators.clear();
    }

    @CheckReturnValue
    @Nonnull
    public List<Number> evaluate() {
        eliminate();
        ArrayList<Number> values = new ArrayList<>(tokens.size());
        int i = 0;
        for (Object object : tokens) {
            if (i % 2 == 0) values.add(((Value) object).get());
            else if (object != Operator.SEPARATOR) throw new ArithmeticException("Expected separator, got " + object);
            i++;
        }
        return values;
    }
}
