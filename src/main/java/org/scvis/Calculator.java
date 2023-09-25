package org.scvis;

import org.scvis.parser.TokenEvaluator;
import org.scvis.parser.TokenParser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Calculator {

    private final @Nonnull InputStream input;

    private final @Nonnull OutputStream output;

    public Calculator(@Nonnull InputStream input, @Nonnull OutputStream output) {
        this.input = input;
        this.output = output;
    }

    public Calculator() {
        this(System.in, System.out);
    }

    @CheckReturnValue
    @Nonnull
    public static List<Number> interpret(@Nonnull String line) {
        TokenParser parser = new TokenParser();
        parser.tokenize(line);
        return new TokenEvaluator(parser.getOperators(), parser.getTokens()).evaluate();
    }

    public void runAndServe() throws IOException {
        try(Scanner scanner = new Scanner(input)) {
            while (true) {
                output.write(">>> ".getBytes());
                String line = scanner.nextLine();
                if (Objects.equals(line, "exit")) break;
                try {
                    Iterator<Number> iterator = interpret(line).listIterator();
                    StringBuilder builder = new StringBuilder();
                    while (iterator.hasNext()) {
                        builder.append(iterator.next());
                        if (iterator.hasNext()) builder.append("; ");
                    }
                    builder.append("\n");
                    output.write(builder.toString().getBytes());
                } catch (Exception e) {
                    String msg = e.getClass().getSimpleName() + ": " + e.getMessage() + "\n";
                    output.write(msg.getBytes());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();
        calculator.runAndServe();
    }
}
