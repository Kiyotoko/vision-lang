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
 *
 */

package org.scvis;

import org.scvis.parser.EvaluationException;
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
    public static List<Object> interpret(@Nonnull String line) {
        TokenParser parser = new TokenParser();
        parser.tokenize(line);
        return new TokenEvaluator(parser.getOperators(), parser.getTokens()).evaluate();
    }

    public void runAndServe() throws IOException {
        try (Scanner scanner = new Scanner(input)) {
            while (true) {
                output.write(">>> ".getBytes());
                String line = scanner.nextLine();
                if (Objects.equals(line, "exit")) break;
                try {
                    Iterator<Object> iterator = interpret(line).listIterator();
                    StringBuilder builder = new StringBuilder();
                    while (iterator.hasNext()) {
                        builder.append(iterator.next());
                        if (iterator.hasNext()) builder.append("; ");
                    }
                    builder.append("\n");
                    output.write(builder.toString().getBytes());
                } catch (EvaluationException e) {
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
