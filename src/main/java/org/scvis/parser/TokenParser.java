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
import java.util.ArrayList;
import java.util.List;

/**
 * TokenParser parses a string into tokens and operators. The tokens contain all operators.
 *
 * @author karlz
 * @see Operator
 */
public class TokenParser {

    private final @Nonnull List<Operator> operators = new ArrayList<>();

    private final @Nonnull List<Object> tokens = new ArrayList<>();

    private final @Nonnull NameSpace nameSpace = NameSpace.buildIns();

    private char[] chars;
    private int pos;

    /**
     * Parses a string from the beginning and stores the tokens.
     *
     * @param string the string to evaluate
     */
    public void tokenize(@Nonnull String string) throws ParsingException, EvaluationException, AccessException {
        tokenize(string.toCharArray(), 0);
    }

    /**
     * Parses a char array from the specified start index and stores the tokens.
     *
     * @param chars the char array
     * @param start the start index
     * @throws ParsingException if the start index is < 0
     */
    public void tokenize(@Nonnull char[] chars, int start) throws ParsingException, EvaluationException,
            AccessException {
        if (start < 0)
            throw new ParsingException("Start index must >= 0", 100);
        for (this.chars = chars, pos = start; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c) || c == '.') {
                tokens.add(parseNumber());
            } else if (Character.isAlphabetic(c)) {
                tokens.add(parseText());
            } else { // Parse one specified char or throw an exception
                switch (c) {
                    case ',':
                    case ';':
                        addOperator(Operator.SEPARATOR);
                        break;
                    case '"':
                    case '\'':
                        tokens.add(parseString());
                        break;
                    case '(':
                        tokens.add(parseBrackets());
                        break;
                    case '[':
                        tokens.add(parseArray());
                        break;
                    case ')':
                    case ']':
                        return;
                    case '=':
                        addOperator(ifNextIs('=') ? ComparisonOperator.EQUALS : DeclarationOperator.DECLARE);
                        break;
                    case '+':
                        addOperator(ifNextIs('=') ? DeclarationOperator.ADD_TO : BinaryOperator.OperatorAndSign.ADD);
                        break;
                    case '-':
                        addOperator(ifNextIs('=') ? DeclarationOperator.SUB_TO : BinaryOperator.OperatorAndSign.SUB);
                        break;
                    case '*':
                        addOperator(ifNextIs('=') ? DeclarationOperator.MUL_TO : BinaryOperator.MUL);
                        break;
                    case '/':
                        addOperator(ifNextIs('=') ? DeclarationOperator.DIV_TO : BinaryOperator.DIV);
                        break;
                    case '^':
                        addOperator(BinaryOperator.POW);
                        break;
                    case '%':
                        addOperator(BinaryOperator.MOD);
                        break;
                    case '!':
                        if (getNext() == '=') addOperator(ComparisonOperator.NOT_EQUALS);
                        else applyFactorial();
                        break;
                    case ' ':
                        break;
                    default:
                        throw new ParsingException("Could not parse char " + c, 110);
                }
            }
        }
    }

    /**
     * Call this method always if you want to parse multiple strings. If not, this may raise an exception.
     *
     * @throws ParsingException TODO
     */
    public void chain() throws ParsingException {
        if (tokens.isEmpty())
            throw new ParsingException("Can not chain parsing before anything was parsed", 120);
        else if (tokens.size() != operators.size() + 1)
            throw new ParsingException("The token list must be by one element greater then the operator list", 130);
        else if (tokens.get(tokens.size() - 1) == Operator.SEPARATOR || operators.get(operators.size() - 1) ==
                Operator.SEPARATOR)
            throw new ParsingException("You can not chain again before evaluating something", 140);
        tokens.add(Operator.SEPARATOR);
        operators.add(Operator.SEPARATOR);
    }

    public void addOperator(Operator operator) {
        tokens.add(operator);
        operators.add(operator);
    }

    @CheckReturnValue
    private char getNext() throws ParsingException {
        int index = pos + 1;
        if (index >= chars.length)
            throw new ParsingException("", 199);
        return chars[index];
    }

    @CheckReturnValue
    private boolean ifNextIs(char c) throws ParsingException {
        if (getNext() == c) {
            pos++;
            return true;
        }
        return false;
    }

    private void applyFactorial() throws ParsingException {
        int index = tokens.size() - 1;
        if (index < 0) throw new ParsingException("Factorial requires a left value", 150);
        Object last = tokens.get(index);
        if (last instanceof Number) {
            tokens.set(index, Stochastic.factorial(((Number) last).intValue()));
        } else {
            throw new ParsingException("Factorial requires a left value", 150);
        }
    }

    @CheckReturnValue
    @Nonnull
    private Object parseVar(@Nonnull String name) {
        return nameSpace.get(name.toLowerCase());
    }

    @CheckReturnValue
    @Nonnull
    private TokenParser parseSubTokens()
            throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = new TokenParser();
        parser.tokenize(chars, pos + 1);
        pos = parser.pos;
        return parser;
    }

    @CheckReturnValue
    @Nonnull
    private Object parseBrackets() throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = parseSubTokens();
        List<Object> values = new TokenEvaluator(parser.operators, parser.tokens).evaluate();
        if (values.size() != 1) {
            throw new ParsingException("Brackets wrap one effective value, got " + values.size(), 170);
        }
        return values.get(0);
    }

    @CheckReturnValue
    @Nonnull
    private Object parseFunction(@Nonnull String name)
            throws ParsingException, AccessException, EvaluationException {
        TokenParser parser = parseSubTokens();
        Object function = nameSpace.get(name).resolve();
        if (function instanceof Callable) {
            try {
                return ((Callable) function).call(new TokenEvaluator(parser.operators, parser.tokens).evaluate());
            } catch (ClassCastException e) {
                throw new AccessException(e);
            }
        } else {
            throw new ParsingException(name + " is no function", 180);
        }
    }

    @CheckReturnValue
    @Nonnull
    private Object parseArray() throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = parseSubTokens();
        return new TokenEvaluator(parser.operators, parser.tokens).evaluate();
    }

    @CheckReturnValue
    @Nonnull
    private Object parseString() {
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
    private Object parseText() throws ParsingException, EvaluationException, AccessException {
        StringBuilder build = new StringBuilder();
        for (; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')
                build.append(chars[pos]);
            else break;
        }
        if (pos < chars.length && chars[pos] == '(') {
            return parseFunction(build.toString());
        } else {
            pos--;
            return parseVar(build.toString());
        }
    }

    @CheckReturnValue
    @Nonnull
    private Object parseNumber() throws ParsingException {
        double build = 0;
        int real = -1;
        for (; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c)) {
                build = build * 10.0 + Character.digit(c, 10);
            } else if (c == '.') {
                if (real > -1)
                    throw new ParsingException("A number can not have two dots", 190);
                real = pos;
            } else {
                pos--;
                break;
            }
        }
        if (real > -1)
            build /= Math.pow(10.0, pos - real);
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
