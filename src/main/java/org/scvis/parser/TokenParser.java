package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.text.ParseException;
import java.util.*;

public class TokenParser {

    public static final @Nonnull Map<Character, Operator> CHARACTER_OPERATOR_MAP = Map.of(
            '+', BaseOperator.ADD, '-', BaseOperator.SUBTRACT, '*', BaseOperator.MULTIPLY,
            '/', BaseOperator.DIVIDE, '%', BaseOperator.MOD, '^', BaseOperator.POW,
            ',', Operator.SEPARATOR, ';', Operator.SEPARATOR);

    private int pos;

    private final @Nonnull List<Operator> operators = new ArrayList<>();

    private final @Nonnull List<Object> tokens = new ArrayList<>();

    public void tokenize(String string) throws ParseException, NoSuchMethodException {
        char[] chars = string.toCharArray();
        for (pos = 0; pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c) || c == '.') {
                tokens.add(parseNumber(chars));
            } else if (Character.isAlphabetic(c)) {
                tokens.add(parseText(chars));
            } else if (CHARACTER_OPERATOR_MAP.containsKey(c)) {
                Operator operator = CHARACTER_OPERATOR_MAP.get(c);
                operators.add(operator);
                tokens.add(operator);
            } else if (c == '(') {
                tokens.add(parseBrackets(chars));
            } else if (!Character.isSpaceChar(c)) {
                throw new ParseException("Could not parse char: ", pos);
            }
        }
    }

    @CheckReturnValue
    @Nonnull
    private Value parseVar(@Nonnull String name) throws NoSuchMethodException {
        Constant constant = Constant.CONSTANT_MAP.get(name.toLowerCase());
        if (constant == null)
            throw new NoSuchMethodException("No variable found for: " + name);
        return constant;
    }

    private TokenParser parseSubTokens(char[] chars) throws ParseException, NoSuchMethodException {
        StringBuilder body = new StringBuilder("(");
        int open = 1;
        for (pos++; open > 0; pos++) {
            char c = chars[pos];
            if (c == '(') open++;
            else if (c == ')') open--;
            body.append(c);
        }
        pos--;
        TokenParser parser = new TokenParser();
        parser.tokenize(body.substring(1, body.length() - 1));
        return parser;
    }

    @CheckReturnValue
    @Nonnull
    private Brackets parseBrackets(@Nonnull char[] chars) throws ParseException, NoSuchMethodException {
        TokenParser parser = parseSubTokens(chars);
        return new Brackets(parser.operators, parser.tokens);
    }

    @CheckReturnValue
    @Nonnull
    private Value parseFunction(@Nonnull String name, @Nonnull char[] chars) throws ParseException,
            NoSuchMethodException {
        TokenParser parser = parseSubTokens(chars);
        return new Function(name, parser.operators, parser.tokens);
    }

    @CheckReturnValue
    @Nonnull
    private Value parseText(@Nonnull char[] chars) throws ParseException, NoSuchMethodException {
        StringBuilder build = new StringBuilder();
        for (;pos < chars.length; pos++) {
            if (Character.isAlphabetic(chars[pos]) || Character.isDigit(chars[pos]))
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
    private Value parseNumber(@Nonnull char[] chars) throws ParseException {
        double build = 0;
        int real = -1;
        for (;pos < chars.length; pos++) {
            char c = chars[pos];
            if (Character.isDigit(c)) {
                if (real > -1) {
                    build += Character.digit(c, 10) / Math.pow(10, pos - real);
                } else {
                    build = build * 10.0 + Character.digit(c, 10);
                }
            } else if (c == '.') {
                if (real > -1)
                    throw new ParseException("Could not parse number: ", pos);
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
    public List<Object> getTokens() {
        return tokens;
    }
}
