/*
 * MIT License
 *
 * Copyright (c) 2023 Karl Zschiebsch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.scvis.parser;

import org.scvis.math.Stochastic;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.*;

/**
 * TokenParser parses a string into tokens and operators. The tokens contain all operators.
 *
 * @author karlz
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

    private final @Nonnull List<Object> tokens = new ArrayList<>();

    private final @Nonnull NameSpace nameSpace = NameSpace.buildIns();

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
                    case '"':
                    case '\'':
                        tokens.add(parseString(chars));
                        break;
                    case '(':
                        tokens.add(parseBrackets(chars));
                        break;
                    case '[':
                        tokens.add(parseArray(chars));
                        break;
                    case ')':
                    case ']':
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
        Object last = tokens.get(index);
        if (last instanceof Number) {
            tokens.set(index, Stochastic.factorial(((Number) last).intValue()));
        } else {
            throw new EvaluationException("Factorial requires a left value");
        }
    }

    @CheckReturnValue
    @Nonnull
    private Object parseVar(@Nonnull String name) {
        Object variable = nameSpace.get(name.toLowerCase());
        if (variable == null)
            throw new EvaluationException("No variable found for " + name);
        return variable;
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
    private Object parseBrackets(@Nonnull char[] chars) {
        TokenParser parser = parseSubTokens(chars);
        List<Object> values = new TokenEvaluator(parser.operators, parser.tokens).evaluate();
        if (values.size() != 1) {
            throw new EvaluationException("Brackets wrap one effective value, got " + values.size());
        }
        return values.get(0);
    }

    @CheckReturnValue
    @Nonnull
    private Object parseFunction(@Nonnull String name, @Nonnull char[] chars) {
        TokenParser parser = parseSubTokens(chars);
        return nameSpace.call(name, new TokenEvaluator(parser.operators, parser.tokens).evaluate());
    }

    @CheckReturnValue
    @Nonnull
    private Object parseArray(@Nonnull char[] chars) {
        TokenParser parser = parseSubTokens(chars);
        return new TokenEvaluator(parser.operators, parser.tokens).evaluate();
    }

    @CheckReturnValue
    @Nonnull
    private Object parseString(@Nonnull char[] chars) {
        char intro = chars[pos++];
        StringBuilder build = new StringBuilder();
        for (; pos < chars.length; pos++) {
            char c = chars[pos];
            if (c != intro)
                build.append(chars[pos]);
            else break;
        }
        return build.toString();
    }

    @CheckReturnValue
    @Nonnull
    private Object parseText(@Nonnull char[] chars) {
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
    private Object parseNumber(@Nonnull char[] chars) {
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
        return build;
    }

    @Nonnull
    public List<Operator> getOperators() {
        return operators;
    }

    @Nonnull
    public List<Object> getTokens() {
        return tokens;
    }
}
