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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class TestFileInterpreter {
    @Test
    void basics() {
        Assertions.assertDoesNotThrow(() -> {
            FileInterpreter interpreter = new FileInterpreter(new File(Objects.requireNonNull(
                    getClass().getResource("basics.vis")).getFile()));
            interpreter.interpretAll();
            interpreter.close();
        });

        try (FileInterpreter interpreter = new FileInterpreter(new File(
                getClass().getResource("basics.vis").getFile()))){
            interpreter.interpretAll();
        } catch (IOException ex) {
            // handle exception
        }
    }

    @Disabled("Uses unimplemented feature")
    @Test
    void variables() {
        Assertions.assertDoesNotThrow(() -> {
            FileInterpreter interpreter = new FileInterpreter(new File(Objects.requireNonNull(
                    getClass().getResource("variables.vis")).getFile()));
            interpreter.interpretAll();
            interpreter.close();
        });
    }

    @Test
    void interpretNext() {
        Assertions.assertDoesNotThrow(() -> {
            FileInterpreter interpreter = new FileInterpreter(new File(Objects.requireNonNull(
                    getClass().getResource("basics.vis")).getFile()));
            interpreter.interpretNext();
            interpreter.close();
        });
    }

    @Test
    void interpretFail() throws IOException {
        FileInterpreter interpreter = new FileInterpreter(new File(Objects.requireNonNull(
                getClass().getResource("basics.vis")).getFile()));
        interpreter.interpretAll();
        Assertions.assertThrows(IOException.class, interpreter::interpretNext);
        interpreter.close();
    }

    @Test
    void interpretAll() {
        Assertions.assertDoesNotThrow(() -> {
            FileInterpreter interpreter = new FileInterpreter(new File(Objects.requireNonNull(
                    getClass().getResource("basics.vis")).getFile()));
            interpreter.interpretAll();
            interpreter.close();
        });
    }
}