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

import javax.annotation.Nonnull;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class NameSpace {

    private static final NameSpace BUILD_INS = new NameSpace();

    /**
     * This constant is currently not available in java 11. You can find <a href="https://bugs.openjdk.org/browse/JDK-8283136">here</a> more.
     */
    private static final double TAU = 6.283185307179586;

    static {
        // From java.lang.Math
        BUILD_INS.declare("sin", args -> Math.sin((double) args.get(0)));
        BUILD_INS.declare("cos", args -> Math.cos((double) args.get(0)));
        BUILD_INS.declare("tan", args -> Math.tan((double) args.get(0)));
        BUILD_INS.declare("asin", args -> Math.asin((double) args.get(0)));
        BUILD_INS.declare("acos", args -> Math.acos((double) args.get(0)));
        BUILD_INS.declare("atan", args -> Math.atan((double) args.get(0)));
        BUILD_INS.declare("sqrt", args -> Math.sqrt((double) args.get(0)));
        BUILD_INS.declare("abs", args -> Math.abs((double) args.get(0)));
        BUILD_INS.declare("signum", args -> Math.signum((double) args.get(0)));
        BUILD_INS.declare("hypot", args -> Math.hypot((double) args.get(0), (double) args.get(0)));

        BUILD_INS.declare("pi", Math.PI);
        BUILD_INS.declare("e", Math.E);
        BUILD_INS.declare("tau", TAU);
    }

    public static NameSpace buildIns() {
        return BUILD_INS;
    }

    private final @Nonnull Map<String, Object> variables = new HashMap<>();

    private final @Nonnull Map<String, Evaluator> functions = new HashMap<>();

    public void declare(@Nonnull String name, @Nonnull Evaluator evaluator) {
        functions.put(name, evaluator);
    }

    public void declare(@Nonnull String name, @Nonnull Object value) {
        variables.put(name, value);
    }

    public Object call(@Nonnull String name, @Nonnull List<Object> args) {
        Evaluator evaluator = functions.get(name);
        if (evaluator == null) throw new EvaluationException("Function not found");
        return evaluator.evaluate(args);
    }

    public Object get(@Nonnull String name) {
        Object variable = variables.get(name);
        if (variable == null) throw new EvaluationException("Variable not initiated");
        return variable;
    }
}
