package org.scvis.parser;

import org.scvis.math.Stochastic;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.*;

/**
 * TokenParser parses a string into tokens and operators. The tokens contain all operators.
 *
 * @author karlz
 * @see Token
 * @see Value
 * @see Operator
 */
public class TokenParser {

    /**
     * A map of all characters to its corresponding value. You can add or change this mapping for your own purpose.
     */
    public static final @Nonnull Map<Character, Operator> CHARACTER_OPERATOR_MAP = Map.of(
            '+', BaseOperator.ADD, '-', BaseOperator.SUBTRACT, '*', BaseOperator.MULTIPLY,
            '/', BaseOperator.DIVIDE, '%', BaseOperator.MOD, '^', BaseOperator.POW,
            ',', Operator.SEPARATOR, ';', Operator.SEPARATOR, '=', BaseOperator.EQU);

    private int pos;

    private final @Nonnull List<Operator> operators = new ArrayList<>();

    private final @Nonnull List<Token> tokens = new ArrayList<>();

    /**
     * Parses a string from the beginning and stores the tokens.
     *
     * @param string the string to evaluate
     */
    public void tokenize(@Nonnull String string) {
        tokenize(string.toCharArray(), 0);
    }

    /**
     * Parses a char array from the specified start index and stores the tokens.
     *
     * @param chars the char array
     * @param start the start index
     * @throws EvaluationException if the start index is < 0
     */
    public void tokenize(@Nonnull char[] chars, int start) {
        if (start < 0)
            throw new EvaluationException("Start index must >= 0");
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
            } else { // Parse one specified char or throw an exception
                switch (c) {
                    case '!':
                        applyFactorial();
                        break;
                    case '(':
                        tokens.add(parseBrackets(chars));
                        break;
                    case ')':
                        return;
                    case ' ':
                        break;
                    default:
                        throw new EvaluationException("Could not parse char " + c);
                }
            }
        }
    }

    /**
     * Call this method always if you want to parse multiple strings. If not, this may raise an exception.
     *
     * @throws EvaluationException TODO
     */
    public void chain() {
        if (tokens.isEmpty())
            throw new EvaluationException("Can not chain parsing before anything was parsed");
        else if (tokens.size() != operators.size() + 1)
            throw new EvaluationException("The token list must be by one element greater then the operator list");
        else if (tokens.get(tokens.size() - 1) == Operator.SEPARATOR || operators.get(operators.size() - 1) ==
                Operator.SEPARATOR)
            throw new EvaluationException("You can not chain again before evaluating something");
        tokens.add(Operator.SEPARATOR);
        operators.add(Operator.SEPARATOR);
    }

    private void applyFactorial() {
        int index = tokens.size() - 1;
        Token last = tokens.get(index);
        if (last.isValue()) {
            tokens.set(index, new NumberValue(Stochastic.factorial(((NumberValue) last).get().intValue())));
        } else {
            throw new EvaluationException("Factorial requires a left value");
        }
    }

    @CheckReturnValue
    @Nonnull
    private NumberValue parseVar(@Nonnull String name) {
        NumberValue numberValue = NumberValue.CONSTANT_MAP.get(name.toLowerCase());
        if (numberValue == null)
            throw new EvaluationException("No variable found for " + name);
        return numberValue;
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
        for (; pos < chars.length; pos++) {
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
        for (; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c)) {
                build = build * 10.0 + Character.digit(c, 10);
            } else if (c == '.') {
                if (real > -1)
                    throw new EvaluationException("Could not parse number by " + c);
                real = pos;
            } else {
                pos--;
                break;
            }
        }
        if (real > -1)
            build /= Math.pow(10.0, pos - real - 1);
        return new NumberValue(build);
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
