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

import org.scvis.Vision;
import org.scvis.VisionException;
import org.scvis.lang.Namespace;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

public class InteractiveInterpreter implements AutoCloseable {

    private final @Nonnull Namespace nameSpace = new BuildInLib();

    private final @Nonnull Scanner scanner;
    private final @Nonnull OutputStream output;

    private boolean closed = false;

    public InteractiveInterpreter(@Nonnull InputStream input, @Nonnull OutputStream output) {
        this.output = output;
        this.scanner = new Scanner(input);
    }

    public InteractiveInterpreter() {
        this(System.in, System.out);
    }

    public void interpretAll() throws IOException {
        while (!closed) {
            interpretNext();
        }
    }

    public void interpretNext() throws IOException {
        output.write(">>> ".getBytes());
        String line = scanner.nextLine();
        if (Objects.equals(line, "exit")) closed = true;
        try {
            printResults(Vision.interpret(nameSpace, line));
        } catch (VisionException exception) {
            printException(exception);
        }
    }

    public void printException(VisionException exception) throws IOException {
        String msg = exception.getClass().getSimpleName() + " [" + exception.getErrorCode() + "]: " + exception.getMessage() + "\n";
        if (exception.getCause() != null) msg += exception.getCause().getClass().getSimpleName() + ": " + exception.getCause().getMessage() + "\n";
        output.write(msg.getBytes());
    }

    public void printResults(Iterable<Object> results) throws IOException {
        Iterator<Object> iterator = results.iterator();
        if (!iterator.hasNext()) return;
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) builder.append("; ");
        }
        builder.append("\n");
        output.write(builder.toString().getBytes());
    }

    @Override
    public void close() {
        scanner.close();
    }
}
