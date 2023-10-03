package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.*;

public class TokenEvaluator {

    private final @Nonnull List<Operator> operators;
    private final @Nonnull List<Token> tokens;

    public TokenEvaluator(@Nonnull List<Operator> operators, @Nonnull List<Token> tokens) {
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
        if (index == 0) {
            Token right = tokens.get(1);
            if (operator.isSign() && right.isValue()) {
                Value evaluated = operator.evaluate(Constant.ZERO, (Value) right);
                tokens.remove(0);
                tokens.set(0, evaluated);
            } else {
                throw new EvaluationException("Operators that are not signs require a left and right value");
            }
        } else {
            Token left = tokens.get(index - 1);
            Token right = tokens.get(index + 1);
            if (left.isValue() && right.isValue()) {
                Value evaluated = operator.evaluate((Value) left, (Value) right);
                tokens.remove(index + 1);
                tokens.remove(index);
                tokens.set(index - 1, evaluated);
            } else {
                throw new EvaluationException("Tokens left and right must be values");
            }
        }
    }

    @CheckReturnValue
    @Nonnull
    public List<Number> evaluate() {
        eliminate();
        ArrayList<Number> values = new ArrayList<>(tokens.size() / 2 + 1);
        int i = 0;
        for (Token token : tokens) {
            if (i % 2 == 0) values.add(((Value) token).get());
            else if (token != Operator.SEPARATOR) throw new EvaluationException("Expected separator, got " + token);
            i++;
        }
        return values;
    }
}
