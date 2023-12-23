package org.scvis.parser;

import org.scvis.lang.Namespace;
import org.scvis.lang.Statement;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class TokenEvaluator {

    private final @Nonnull List<Operator> operators;
    private final @Nonnull List<Object> tokens;
    private final @Nonnull Namespace nameSpace;

    public TokenEvaluator(@Nonnull Namespace nameSpace, @Nonnull List<Operator> operators, @Nonnull List<Object> tokens) {
        this.nameSpace = nameSpace;
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
        if (index == tokens.size() - 1)
            throw new EvaluationException("A right value must exist", 230);
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

    /**
     *
     * @ a list of evaluated values
     * @throws EvaluationException if an exception during parsing occurs
     * @throws AccessException if an exception during accessing an object occurs
     * @throws ParsingException if an exception during parsing occurs
     */
    @CheckReturnValue
    @Nonnull
    public List<Object> evaluate() throws EvaluationException, AccessException, ParsingException {
        eliminate();
        ListIterator<Object> iterator = tokens.listIterator();
        while (iterator.hasNext()) {
            Object token = iterator.next();
                if (token instanceof Statement) ((Statement) token).execute(nameSpace);
                else if (token == Operator.SEPARATOR) iterator.remove();
        }
        return tokens;
    }
}
