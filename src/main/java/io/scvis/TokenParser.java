package io.scvis;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TokenParser {

    private static final @Nonnull Map<Character, Operator> CHARACTER_OPERATOR_MAP = Map.of(
            '+', BaseOperator.ADD, '-', BaseOperator.SUBTRACT, '*', BaseOperator.MULTIPLY,
            '/', BaseOperator.DIVIDE, '%', BaseOperator.MOD, '^', BaseOperator.POW);

    private int pos;

    private final @Nonnull List<Operator> operators = new ArrayList<>();

    private final @Nonnull List<Object> tokens = new ArrayList<>();

    public void tokenize(String string) throws ParseException, NoSuchMethodException {
        char[] chars = string.toCharArray();
        pos = 0; // reset position
        while (pos < chars.length) {
            char c = chars[pos];
            if (Character.isDigit(c) || c == '.') {
                tokens.add(parseNumber(chars));
            } else if (Character.isAlphabetic(c)) {
                tokens.add(parseText(chars));
            } else if (CHARACTER_OPERATOR_MAP.containsKey(c)) {
                Operator operator = CHARACTER_OPERATOR_MAP.get(c);
                tokens.add(operator);
                operators.add(operator);
            } else if (c == '(') {
                tokens.add(parseBrackets(chars));
            } else if (!Character.isSpaceChar(c)) {
                throw new ParseException("Could not parse char: ", pos);
            }
            pos++;
        }
    }

    @CheckReturnValue
    @Nonnull
    private Value parseVar(String name) throws NoSuchMethodException {
        Constant constant = Constant.CONSTANT_MAP.get(name.toLowerCase());
        if (constant == null)
            throw new NoSuchMethodException("No variable found for: " + name);
        return constant;
    }

    @CheckReturnValue
    @Nonnull
    private Brackets parseBrackets(char[] chars) throws ParseException, NoSuchMethodException {
        StringBuilder body = new StringBuilder("(");
        int open = 1;
        pos++;
        while (open > 0) {
            char c = chars[pos];
            if (c == '(') open++;
            else if (c == ')') open--;
            body.append(c);
            pos++;
        }
        pos--;
        TokenParser parser = new TokenParser();
        parser.tokenize(body.substring(1, body.length() - 1));
        return new Brackets(parser.operators, parser.tokens);
    }

    @CheckReturnValue
    @Nonnull
    private Value parseFunction(String name, char[] chars) throws ParseException, NoSuchMethodException {
        return new Function(name, parseBrackets(chars));
    }

    @CheckReturnValue
    @Nonnull
    private Value parseText(char[] chars) throws ParseException, NoSuchMethodException {
        StringBuilder build = new StringBuilder();
        while (pos < chars.length && (Character.isAlphabetic(chars[pos]) || Character.isDigit(chars[pos]))) {
            build.append(chars[pos]);
            pos++;
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
    private Value parseNumber(char[] chars) throws ParseException {
        double build = 0;
        int real = -1;
        while (pos < chars.length) {
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
            pos++;
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
