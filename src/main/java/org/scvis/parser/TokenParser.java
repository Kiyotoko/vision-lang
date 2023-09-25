package org.scvis.parser;

import org.scvis.math.Stochastic;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.*;

public class TokenParser {

    public static final @Nonnull Map<Character, Operator> CHARACTER_OPERATOR_MAP = Map.of(
            '+', BaseOperator.ADD, '-', BaseOperator.SUBTRACT, '*', BaseOperator.MULTIPLY,
            '/', BaseOperator.DIVIDE, '%', BaseOperator.MOD, '^', BaseOperator.POW,
            ',', Operator.SEPARATOR, ';', Operator.SEPARATOR, '=', BaseOperator.EQU);

    private int pos;

    private final @Nonnull List<Operator> operators = new ArrayList<>();

    private final @Nonnull List<Token> tokens = new ArrayList<>();

    public void tokenize(@Nonnull String string) {
        tokenize(string.toCharArray(), 0);
    }

    public void tokenize(@Nonnull char[] chars, int start) {
        for (pos = start; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c) || c == '.') {
                tokens.add(parseNumber(chars));
            } else if (Character.isAlphabetic(c)) {
                tokens.add(parseText(chars));
            } else if (CHARACTER_OPERATOR_MAP.containsKey(c)) {
                Operator operator = CHARACTER_OPERATOR_MAP.get(c);
                operators.add(operator);
                tokens.add(operator);
            } else if (c == '!') {
                applyFactorial();
            } else if (c == '(') {
                tokens.add(parseBrackets(chars));
            } else if (c == ')') {
                break;
            }   else if (c != ' ') {
                throw new EvaluationException("Could not parse char " + c);
            }
        }
    }

    private void applyFactorial() {
        int index = tokens.size() - 1;
        Token last = tokens.get(index);
        if (last.isValue()) {
            tokens.set(index, new Constant(Stochastic.factorial(((Value) last).get().intValue())));
        } else {
            throw new EvaluationException("Factorial requires a left value");
        }
    }

    @CheckReturnValue
    @Nonnull
    private Constant parseVar(@Nonnull String name) {
        Constant constant = Constant.CONSTANT_MAP.get(name.toLowerCase());
        if (constant == null)
            throw new EvaluationException("No variable found for " + name);
        return constant;
    }

    @CheckReturnValue
    @Nonnull
    private TokenParser parseSubTokens(@Nonnull char[] chars) {
        TokenParser parser = new TokenParser();
        parser.tokenize(chars, pos + 1);
        pos = parser.pos;
        return parser;
    }

    @CheckReturnValue
    @Nonnull
    private Brackets parseBrackets(@Nonnull char[] chars) {
        TokenParser parser = parseSubTokens(chars);
        return new Brackets(parser.operators, parser.tokens);
    }

    @CheckReturnValue
    @Nonnull
    private Function parseFunction(@Nonnull String name, @Nonnull char[] chars) {
        TokenParser parser = parseSubTokens(chars);
        return new Function(name, parser.operators, parser.tokens);
    }

    @CheckReturnValue
    @Nonnull
    private Token parseText(@Nonnull char[] chars) {
        StringBuilder build = new StringBuilder();
        for (;pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')
                build.append(chars[pos]);
            else break;
        }
        if (pos < chars.length && chars[pos] == '(') {
            return parseFunction(build.toString(), chars);
        } else {
            pos--;
            return parseVar(build.toString());
        }
    }

    @CheckReturnValue
    @Nonnull
    private Token parseNumber(@Nonnull char[] chars) {
        double build = 0;
        int real = -1;
        for (;pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c)) {
                if (real > -1) {
                    build += Character.digit(c, 10) / Math.pow(10.0, pos - real);
                } else {
                    build = build * 10.0 + Character.digit(c, 10);
                }
            } else if (c == '.') {
                if (real > -1)
                    throw new EvaluationException("Could not parse number by " + c);
                real = pos;
            } else {
                pos--;
                break;
            }
        }
        return new Constant(build);
    }

    @Nonnull
    public List<Operator> getOperators() {
        return operators;
    }

    @Nonnull
    public List<Token> getTokens() {
        return tokens;
    }
}
