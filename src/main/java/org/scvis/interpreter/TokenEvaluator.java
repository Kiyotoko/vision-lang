package org.scvis.parser;

import org.scvis.VisionException;
import org.scvis.lang.Namespace;
import org.scvis.lang.Operator;
import org.scvis.lang.Statement;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TokenEvaluator {

    private final @Nonnull Namespace namespace;
    private final @Nonnull Statement statement;

    public TokenEvaluator(@Nonnull Namespace namespace, @Nonnull Statement statement) {
        this.namespace = namespace;
        this.statement = statement;
        Collections.sort(statement.operators);
    }

    private void eliminate() {
        for (Operator operator : statement.operators) {
            if (operator == Operators.SEPARATOR) break;
            eliminate(operator);
        }
    }

    @SuppressWarnings("unchecked")
    private void eliminate(Operator operator) {
        int index = statement.tokens.indexOf(operator);
        if (index == -1)
            throw new VisionException("Operator '" + operator + "' must be present in tokens", 200);
        if (index == statement.tokens.size() - 1)
            throw new VisionException("A right value must exist", 230);
        if (index == 0) {
            Object right = statement.tokens.get(1);
            if (operator instanceof Operators.Sign) {
                Object evaluated = operator.apply(0.0, right);
                statement.tokens.remove(0);
                statement.tokens.set(0, evaluated);
            } else {
                throw new VisionException("Operators that are not signs require a left and right value", 210);
            }
        } else {
            Object evaluated = operator.apply(statement.tokens.get(index - 1), statement.tokens.get(index + 1));
            statement.tokens.remove(index + 1);
            statement.tokens.remove(index);

            if (evaluated instanceof Consumer)
                ((Consumer<Namespace>) evaluated).accept(namespace);
            else
                statement.tokens.set(index - 1, evaluated);
        }
    }

    /**
     * @return a list of evaluated values
     */
    @CheckReturnValue
    @Nonnull
    public List<Object> evaluate() {
        eliminate();
        statement.tokens.removeIf(token -> token == Operators.SEPARATOR);
        return statement.tokens;
    }
}
