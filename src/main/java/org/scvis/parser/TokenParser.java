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

import org.scvis.lang.Callable;
import org.scvis.lang.Namespace;
import org.scvis.lang.Statement;
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

    private final @Nonnull Namespace nameSpace;

    private char[] chars;
    private int pos;
    private char bracket = 0x0;

    public TokenParser(@Nonnull Namespace nameSpace) {
        this.nameSpace = nameSpace;
    }

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
            } else if (Character.isAlphabetic(c) || c == '_') {
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
                    case '{':
                        tokens.add(parseBlock());
                        break;
                    case ')':
                    case ']':
                    case '}':
                        checkClosedBracket();
                        return;
                    case '=':
                        addOperator(ifNextIs('=') ? ComparisonOperator.IS_EQUALS : DeclarationOperator.DECLARE);
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
                        if (ifNextIs('=')) addOperator(ComparisonOperator.NOT_EQUALS);
                        else applyFactorial();
                        break;
                    case ' ':
                    case '\n':
                    case '\t':
                        break;
                    default:
                        throw new ParsingException("Could not parse char '" + c + "'", 110);
                }
            }
        }
        if (bracket != 0x0) throw new ParsingException("Bracket '" + bracket + "' was not closed", 192);
    }

    /**
     * Call this method always if you want to parse multiple strings. If not, this may raise an exception.
     *
     * @throws ParsingException if no tokens where parsed, the last token or operator is a separator or if the size of
     * tokens and operators do not match.
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

    public void addOperator(@Nonnull Operator operator) {
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
            tokens.set(index, Stochastic.factorial(((Number) last).shortValue()));
        } else {
            throw new ParsingException("Factorial requires a left value", 150);
        }
    }

    private void skipWhiteSpaces() {
        for (int index = pos + 1; index < chars.length; index++) {
            if (chars[index] != ' ') {
                pos = index - 1;
                return;
            }
        }
        pos = chars.length;
    }

    private void checkClosedBracket() throws ParsingException {
        if (bracket == 0x0) throw new ParsingException("Bracket is null, maybe you want to remove a closing bracket?", 194);
        if (chars[pos] != bracket) {
            throw new ParsingException("Closed Bracket '" + chars[pos] +"' does not match opened bracket '" + bracket + "'!", 197);
        }
    }

    private Statement parseBlock() {
        throw new UnsupportedOperationException();
    }

    @CheckReturnValue
    @Nonnull
    private Object parseText() throws ParsingException, EvaluationException, AccessException {
        StringBuilder build = new StringBuilder();
        Namespace working = nameSpace;
        for (; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')
                build.append(chars[pos]);
            else if (c == '.') {
                Object space = working.get(build.toString()).resolve();
                if (space instanceof Namespace) {
                    working = (Namespace) space;
                    build = new StringBuilder();
                } else {
                    throw new AccessException(new ClassCastException(space.toString()));
                }
            } else break;
        }
        if (pos < chars.length && chars[pos] == '(') {
            return parseFunction(working, build.toString());
        } else {
            pos--;
            return parseVar(working, build.toString());
        }
    }

    @CheckReturnValue
    @Nonnull
    private Object parseFunction(@Nonnull Namespace working, @Nonnull String name)
            throws ParsingException, AccessException, EvaluationException {
        TokenParser parser = parseSubTokens();
        Object function = working.get(name).resolve();
        if (function instanceof Callable) {
            if (function instanceof Introducer) {
                skipWhiteSpaces();
                if (ifNextIs('{')) {
                    ((Introducer) function).introduce(parseBlock());
                }
            }
            try {
                return ((Callable) function).call(new TokenEvaluator(working, parser.operators, parser.tokens).evaluate());
            } catch (ClassCastException e) {
                throw new AccessException(e);
            }
        } else {
            throw new ParsingException(name + " is not callable", 180);
        }
    }

    @CheckReturnValue
    @Nonnull
    private Object parseVar(@Nonnull Namespace working, @Nonnull String name) {
        return working.get(name);
    }

    @CheckReturnValue
    @Nonnull
    private TokenParser parseSubTokens()
            throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = new TokenParser(nameSpace);
        char opened = chars[pos];
        parser.bracket = opened == '(' ? ')' : (char) (opened + 2);
        parser.tokenize(chars, pos + 1);
        pos = parser.pos;
        return parser;
    }

    @CheckReturnValue
    @Nonnull
    private Object parseBrackets() throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = parseSubTokens();
        List<Object> values = new TokenEvaluator(nameSpace, parser.operators, parser.tokens).evaluate();
        if (values.size() != 1) {
            throw new ParsingException("Brackets wrap one effective value, got " + values.size(), 170);
        }
        return values.get(0);
    }

    @CheckReturnValue
    @Nonnull
    private Object parseArray() throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = parseSubTokens();
        return new TokenEvaluator(nameSpace, parser.operators, parser.tokens).evaluate();
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
    private Number parseNumber() throws ParsingException {
        long build = 0;
        int real = -1;
        for (; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c)) {
                build = build * 10 + Character.digit(c, 10);
            } else if (c == '.') {
                if (real > -1)
                    throw new ParsingException("A number can not have two dots", 190);
                real = pos;
            } else {
                break;
            }
        }
        pos--;
        if (real > -1) {
            @SuppressWarnings("all")
            var power = Math.pow(10.0, pos - real);
            return build * power;
        }
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
