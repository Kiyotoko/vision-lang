package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenEvaluator {

    private final @Nonnull List<Operator> operators;
    private final @Nonnull List<Object> tokens;

    public TokenEvaluator(@Nonnull List<Operator> operators, @Nonnull List<Object> tokens) {
        this.operators = operators;
        Collections.sort(operators);
        this.tokens = tokens;
    }

    private void eliminate() throws EvaluationException, AccessException {
        for (Operator operator : operators) {
            if (operator == Operator.SEPARATOR) break;
            eliminate(operator);
        }
        operators.clear();
    }

    private void eliminate(Operator operator) throws EvaluationException, AccessException {
        int index = tokens.indexOf(operator);
        if (index == -1)
            throw new EvaluationException("Operator must be present in tokens", 200);
        if (index == 0) {
            Object right = tokens.get(1);
            if (operator instanceof Sign) {
                try {
                    Object evaluated = operator.evaluate(0.0, right);
                    tokens.remove(0);
                    tokens.set(0, evaluated);
                } catch (ClassCastException e) {
                    throw new AccessException(e);
                }
            } else {
                throw new EvaluationException("Operators that are not signs require a left and right value", 210);
            }
        } else {
            try {
                Object evaluated = operator.evaluate(tokens.get(index - 1), tokens.get(index + 1));
                tokens.remove(index + 1);
                tokens.remove(index);
                tokens.set(index - 1, evaluated);
            } catch (ClassCastException e) {
                throw new AccessException(e);
            }
        }
    }

    @CheckReturnValue
    @Nonnull
    public List<Object> evaluate() throws EvaluationException, AccessException {
        eliminate();
        ArrayList<Object> values = new ArrayList<>(tokens.size() / 2 + 1);
        int i = 0;
        for (Object token : tokens) {
            if (i % 2 == 0) values.add(token);
            else if (token != Operator.SEPARATOR)
                throw new EvaluationException("Expected separator, got " + token.getClass().getSimpleName(), 220);
            i++;
        }
        return values;
    }
}
