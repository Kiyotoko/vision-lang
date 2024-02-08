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

import org.scvis.ScVis;
import org.scvis.ScVisException;
import org.scvis.lang.BuildInLib;
import org.scvis.lang.Namespace;

import java.io.*;

public class FileInterpreter {

    File file;
    Namespace namespace;

    @SuppressWarnings("all")
    FileInterpreter(File file) throws IOException {
        this.file = file;
        this.namespace  = new FileNamespace();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int linenumber = 0;
            while ((line = reader.readLine()) != null) {
                linenumber++;
                try {
                    ScVis.interpret(namespace, line);
                } catch (Exception ex) {
                    new RuntimeException("Got an exception in line " + linenumber, ex);
                }
            }
        }
    }

    public class FileNamespace extends BuildInLib {
        FileNamespace() {
            set("__file__", file.getAbsoluteFile());
            set("__path__", file.getAbsolutePath());
        }
    }
}
