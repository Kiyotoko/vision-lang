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

import org.scvis.ScVis;
import org.scvis.lang.Callable;
import org.scvis.lang.Library;
import org.scvis.lang.Namespace;

public class MathLib extends Library {

    /**
     * This constant is currently not available in java 11. You can find <a
     * href="https://bugs.openjdk.org/browse/JDK-8283136">here</a> more.
     */
    private static final double TAU = 6.283185307179586;

    public MathLib() {

        // From java.lang.Math
        set("sin", (Callable) args -> Math.sin(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("cos", (Callable) args -> Math.cos(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("tan", (Callable) args -> Math.tan(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("asin", (Callable) args -> Math.asin(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("acos", (Callable) args -> Math.acos(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("atan", (Callable) args -> Math.atan(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("sqrt", (Callable) args -> Math.sqrt(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("abs", (Callable) args -> Math.abs(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("signum", (Callable) args -> Math.signum(ScVis.asFloat(ScVis.getArg(args, 0))));
        set("hypot", (Callable) args -> Math.hypot(ScVis.asFloat(ScVis.getArg(args, 0)),
                ScVis.asFloat(ScVis.getArg(args, 1))));

        set("pi", Math.PI);
        set("e", Math.E);
        set("tau", TAU);
    }
}
