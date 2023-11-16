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
            eliminate(operator);
        }
        operators.clear();
    }

    private void eliminate(Operator operator) {
        int index = tokens.indexOf(operator);
        if (index == -1)
            throw new EvaluationException("Operator must be present in tokens");
        if (index == 0) {
            Object right = tokens.get(1);
            if (operator.isSign() && !(right instanceof Operator)) {
                Object evaluated = operator.evaluate(0.0, right);
                tokens.remove(0);
                tokens.set(0, evaluated);
            } else {
                throw new EvaluationException("Operators that are not signs require a left and right value");
            }
        } else {
            Object left = tokens.get(index - 1);
            Object right = tokens.get(index + 1);
            if (!(left instanceof Operator) && !(right instanceof Operator)) {
                Object evaluated = operator.evaluate(left, right);
                tokens.remove(index + 1);
                tokens.remove(index);
                tokens.set(index - 1, evaluated);
            } else {
                throw new EvaluationException("Tokens left and right must not be operators");
            }
        }
    }

    @CheckReturnValue
    @Nonnull
    public List<Object> evaluate() {
        eliminate();
        ArrayList<Object> values = new ArrayList<>(tokens.size() / 2 + 1);
        int i = 0;
        for (Object token : tokens) {
            if (i % 2 == 0) values.add(token);
            else if (token != Operator.SEPARATOR)
                throw new EvaluationException("Expected separator, got " + token.getClass().getSimpleName());
            i++;
        }
        return values;
    }
}
