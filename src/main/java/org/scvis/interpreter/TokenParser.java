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

package org.scvis.interpreter;

import org.scvis.VisionException;
import org.scvis.lang.*;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * TokenParser parses a string into tokens and operators. The tokens contain all operators.
 *
 * @author karlz
 * @see Operator
 */
public class TokenParser {

    private final @Nonnull Namespace namespace;
    private final @Nonnull Statement statement;

    private char[] chars;
    private int pos;
    private char bracket = 0x0;

    public TokenParser(@Nonnull Namespace namespace) {
        this.namespace = namespace;
        this.statement = new Statement();
    }

    /**
     * Parses a string from the beginning and stores the tokens.
     *
     * @param string the string to evaluate
     */
    public void parse(@Nonnull String string) {
        parse(string.toCharArray(), 0);
    }

    /**
     * Parses a char array from the specified start index and stores the tokens.
     * <code>
    Function ( ... )
    Pair = Value + Operator
    Value = Function | Var | Number | String
     </code>
     *
     * @param chars the char array
     * @param start the start index
     */
    public void parse(@Nonnull char[] chars, int start) {
        this.chars = chars;
        this.pos = start;

        if (chars.length == 0) return;
        parseStatement();
    }

    private void skipWhiteSpaces() {
        char c;
        while (hasCharsLeft() && ((c = chars[pos]) == ' ' || c == '\t' || c == '\n')) {
            pos++;
        }
    }

    public void parseStatement() {
        skipWhiteSpaces();
        parseValue();
        skipWhiteSpaces();
        if (hasCharsLeft() && hasNotClosed())
            parsePair();
    }

    public void parsePair() {
        parseOperator();
        skipWhiteSpaces();
        parseValue();
        skipWhiteSpaces();
        if (hasCharsLeft() && hasNotClosed())
            parsePair();
    }

    public void parseValue() {
        char current = chars[pos];
        if (Character.isDigit(current) || current == '.')
            statement.tokens.add(getNextNumber());
        else if (Character.isAlphabetic(current) || current == '_')
            parseLabel();
        else if (current == '"' || current == '\'')
            statement.tokens.add(getNextString());
        else if (current == '(')
            statement.tokens.add(getNextWrapped().get(0));
        else if (current == '[')
            statement.tokens.add(getNextWrapped());
    }

    public void parseLabel() {
        Label label = getNextLabel();

        skipWhiteSpaces();
        if (hasCharsLeft() && chars[pos] == '(') {
            parseFunction(label);
        } else {
            if (statement.tokens.isEmpty() && bracket == 0x0) {
                statement.tokens.add(label);
            } else {
                statement.tokens.add(label.getResource());
                skipWhiteSpaces();
                if (hasCharsLeft() && hasNotClosed()) parsePair();
            }
        }
    }

    public void parseOperator() {
        var current = chars[pos++];
        switch (current) {
            case '=':
                statement.addOperator(hasNextEquals() ? Operators.IS_EQUALS : Operators.DECLARE);
                break;
            case '+':
                statement.addOperator(hasNextEquals() ? Operators.ADD_TO : Operators.Sign.ADD);
                break;
            case '-':
                statement.addOperator(hasNextEquals() ? Operators.SUB_TO : Operators.Sign.SUB);
                break;
            case '*':
                statement.addOperator(hasNextEquals() ? Operators.MUL_TO : Operators.MUL);
                break;
            case '/':
                statement.addOperator(hasNextEquals() ? Operators.DIV_TO : Operators.DIV);
                break;
            case '^':
                statement.addOperator(Operators.POW);
                break;
            case '%':
                statement.addOperator(Operators.MOD);
                break;
            case ',':
                statement.addOperator(Operators.SEPARATOR);
                break;
            case '!':
                if (hasNextEquals()) {
                    statement.addOperator(Operators.NOT_EQUALS);
                    break;
                }
                throw new VisionException("Operator requires postfix", 123);
            default:
                throw new VisionException("Char '" + current + "' is not an operator", 110);
        }
    }

    public void parseFunction(@Nonnull Label label) {
        TokenParser parser = getNextSubTokens();
        Object function = label.getResource();
        if (function instanceof Callable) {
            statement.tokens.add(((Callable) function).apply(new TokenEvaluator(namespace, parser.statement).evaluate()));
        } else {
            throw new VisionException("Label '" + label.getName() + "' is not callable", 180);
        }
    }

    @CheckReturnValue
    @Nonnull
    public Number getNextNumber() {
        long build = 0;
        int real = -1;
        while (hasCharsLeft()) {
            char c = chars[pos++];
            if (Character.isDigit(c)) {
                build = build * 10 + Character.digit(c, 10);
            } else if (c == '.') {
                if (real > -1)
                    throw new VisionException("Numbers can not have two dots", 190);
                real = pos;
            } else {
                pos--;
                break;
            }
        }

        if (real > -1) {
            @SuppressWarnings("all")
            var power = Math.pow(10.0, pos - real + 1);
            return build * power;
        } else return build;
    }

    @CheckReturnValue
    @Nonnull
    private String getNextString() {
        StringBuilder build = new StringBuilder();
        for (char intro = chars[pos++]; pos < chars.length; pos++) {
            char c = chars[pos];
            if (c != intro)
                build.append(chars[pos]);
            else break;
        }
        return build.toString();
    }

    @CheckReturnValue
    @Nonnull
    public List<Object> getNextWrapped() {
        TokenParser parser = getNextSubTokens();
        return new TokenEvaluator(namespace, parser.statement).evaluate();
    }

    @CheckReturnValue
    @Nonnull
    public Label getNextLabel() {
        StringBuilder build = new StringBuilder();
        Namespace working = namespace;
        while (hasCharsLeft()) {
            char c = chars[pos++];
            if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')
                build.append(c);
            else if (c == '.') {
                var next = working.get(build.toString());
                if (next instanceof Namespace) working = (Namespace) next;
                else throw new VisionException("Pointer is not a namespace", 328);
                build = new StringBuilder();
            }
            else {
                pos--;
                break;
            }
        }
        return new Label(working, build.toString());
    }

    private TokenParser getNextSubTokens() {
        TokenParser parser = new TokenParser(namespace);
        char opened = chars[pos];
        parser.bracket = opened == '(' ? ')' : (char) (opened + 2);
        parser.parse(chars, ++pos);
        pos = parser.pos + 1;
        return parser;
    }

    @CheckReturnValue
    private boolean hasNextEquals() {
        if (!hasCharsLeft())
            throw new VisionException("No other chars left", 199);
        return chars[pos] == '=';
    }

    @CheckReturnValue
    private boolean hasCharsLeft() {
        return pos < chars.length;
    }

    @CheckReturnValue
    private boolean hasNotClosed() {
        return chars[pos] != ')' && chars[pos] != ']';
    }

    @SuppressWarnings("unused")
    private void checkClosedBracket() {
        if (bracket == 0x0) throw new VisionException("Bracket is null, maybe you want to remove a closing bracket?", 194);
        if (chars[pos] != bracket) {
            throw new VisionException("Closed Bracket '" + chars[pos] + "' does not match opened bracket '" + bracket + "'!", 197);
        }
    }

    @CheckReturnValue
    @Nonnull
    public Statement getStatement() {
        return statement;
    }
}
