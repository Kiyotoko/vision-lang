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

package org.scvis.math;

import org.scvis.parser.Callable;
import org.scvis.parser.NameSpace;

import static org.scvis.parser.Callable.num;

public class MathLib extends NameSpace {

    /**
     * This constant is currently not available in java 11. You can find <a
     * href="https://bugs.openjdk.org/browse/JDK-8283136">here</a> more.
     */
    private static final double TAU = 6.283185307179586;

    public MathLib() {

        // From java.lang.Math
        set("sin", (Callable) args -> Math.sin(num(args, 0).doubleValue()));
        set("cos", (Callable) args -> Math.cos(num(args, 0).doubleValue()));
        set("tan", (Callable) args -> Math.tan(num(args, 0).doubleValue()));
        set("asin", (Callable) args -> Math.asin(num(args, 0).doubleValue()));
        set("acos", (Callable) args -> Math.acos(num(args, 0).doubleValue()));
        set("atan", (Callable) args -> Math.atan(num(args, 0).doubleValue()));
        set("sqrt", (Callable) args -> Math.sqrt(num(args, 0).doubleValue()));
        set("abs", (Callable) args -> Math.abs(num(args, 0).doubleValue()));
        set("signum", (Callable) args -> Math.signum(num(args, 0).doubleValue()));
        set("hypot",
                (Callable) args -> Math.hypot(num(args, 0).doubleValue(), num(args, 1).doubleValue()));

        set("pi", Math.PI);
        set("e", Math.E);
        set("tau", TAU);
    }
}
