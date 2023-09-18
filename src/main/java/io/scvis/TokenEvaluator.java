package io.scvis;

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
            int index = tokens.indexOf(operator);
            if (index == 0) {
                if (operator.zeroAsLeft()) {
                    Value evaluated = operator.evaluate(Constant.ZERO, (Value) tokens.get(1));
                    tokens.remove(1);
                    tokens.remove(0);
                    tokens.add(0, evaluated);
                } else {
                    throw new UnsupportedOperationException("Operator does not allow zero as left!");
                }
            } else {
                Value evaluated = operator.evaluate((Value) tokens.get(index - 1), (Value) tokens.get(index + 1));
                for (int i = 1; i > -2; i--) {
                    tokens.remove(index + i);
                }
                tokens.add(index - 1, evaluated);
            }
        }
    }

    public Number evaluate() {
        eliminate();
        if (tokens.size() != 1) {
            throw new ArithmeticException("False number of tokens are left after elimination: " + tokens.size());
        }
        return ((Value) tokens.get(0)).get();
    }
}
