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

import org.scvis.Vision;
import org.scvis.lang.Callable;
import org.scvis.lang.Library;

public class MathLib extends Library {

    /**
     * This constant is currently not available in java 11. You can find <a
     * href="https://bugs.openjdk.org/browse/JDK-8283136">here</a> more.
     */
    private static final double TAU = 6.283185307179586;

    public MathLib() {

        // From java.lang.Math
        set("sin", (Callable) args -> Math.sin(Vision.asFloat(Vision.getArg(args, 0))));
        set("cos", (Callable) args -> Math.cos(Vision.asFloat(Vision.getArg(args, 0))));
        set("tan", (Callable) args -> Math.tan(Vision.asFloat(Vision.getArg(args, 0))));
        set("asin", (Callable) args -> Math.asin(Vision.asFloat(Vision.getArg(args, 0))));
        set("acos", (Callable) args -> Math.acos(Vision.asFloat(Vision.getArg(args, 0))));
        set("atan", (Callable) args -> Math.atan(Vision.asFloat(Vision.getArg(args, 0))));
        set("sqrt", (Callable) args -> Math.sqrt(Vision.asFloat(Vision.getArg(args, 0))));
        set("abs", (Callable) args -> Math.abs(Vision.asFloat(Vision.getArg(args, 0))));
        set("signum", (Callable) args -> Math.signum(Vision.asFloat(Vision.getArg(args, 0))));
        set("hypot", (Callable) args -> Math.hypot(Vision.asFloat(Vision.getArg(args, 0)),
                Vision.asFloat(Vision.getArg(args, 1))));

        set("pi", Math.PI);
        set("e", Math.E);
        set("tau", TAU);
    }
}
