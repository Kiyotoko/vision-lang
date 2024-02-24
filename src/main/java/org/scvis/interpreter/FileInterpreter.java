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

import org.scvis.lang.Namespace;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.*;

public class FileInterpreter implements AutoCloseable {

    private final @Nonnull BufferedReader reader;
    private final @Nonnull Namespace namespace;

    @SuppressWarnings("all")
    public FileInterpreter(@Nonnull File file) throws IOException {
        this.reader = new BufferedReader(new FileReader(file));
        this.namespace  = new FileNamespace(file);
    }

    public static class FileNamespace extends BuildInLib {
        FileNamespace(File file) {
            set("__file__", file.getAbsoluteFile());
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    protected int lineNumber = 0;

    public void interpretOne() throws IOException {
        String line = reader.readLine();
        if (line != null) {
            lineNumber++;
            try {
                TokenParser parser = new TokenParser(namespace);
                parser.parse(line);
                new TokenEvaluator(namespace, parser.getStatement()).evaluate();
            } catch (Exception ex) {
                throw new IOException("Exception in line " + lineNumber, ex);
            }
        } else throw new IOException("No line left");
    }

    public void interpretAll() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            try {
                TokenParser parser = new TokenParser(namespace);
                parser.parse(line);
                new TokenEvaluator(namespace, parser.getStatement()).evaluate();
            } catch (Exception ex) {
                throw new IOException("Exception in line " + lineNumber, ex);
            }
        }
    }

    @CheckReturnValue
    @Nonnull
    public Namespace getNamespace() {
        return namespace;
    }
}
