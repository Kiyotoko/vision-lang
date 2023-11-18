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
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class NameSpace {

    private static final NameSpace BUILD_INS = new NameSpace();

    /**
     * This constant is currently not available in java 11. You can find <a
     * href="https://bugs.openjdk.org/browse/JDK-8283136">here</a> more.
     */
    private static final double TAU = 6.283185307179586;

    static {
        // From java.lang.Math
        BUILD_INS.declare("sin", (Callable) args -> Math.sin(num(args, 0).doubleValue()));
        BUILD_INS.declare("cos", (Callable) args -> Math.cos(num(args, 0).doubleValue()));
        BUILD_INS.declare("tan", (Callable) args -> Math.tan(num(args, 0).doubleValue()));
        BUILD_INS.declare("asin", (Callable) args -> Math.asin(num(args, 0).doubleValue()));
        BUILD_INS.declare("acos", (Callable) args -> Math.acos(num(args, 0).doubleValue()));
        BUILD_INS.declare("atan", (Callable) args -> Math.atan(num(args, 0).doubleValue()));
        BUILD_INS.declare("sqrt", (Callable) args -> Math.sqrt(num(args, 0).doubleValue()));
        BUILD_INS.declare("abs", (Callable) args -> Math.abs(num(args, 0).doubleValue()));
        BUILD_INS.declare("signum", (Callable) args -> Math.signum(num(args, 0).doubleValue()));
        BUILD_INS.declare("hypot", (Callable) args -> Math.hypot(num(args, 0).doubleValue(), num(args, 1).doubleValue()));

        BUILD_INS.declare("pi", Math.PI);
        BUILD_INS.declare("e", Math.E);
        BUILD_INS.declare("tau", TAU);
    }

    @SuppressWarnings("unchecked")
    private static <T> T obj(List<Object> args, int index) throws AccessException {
        if (index >= args.size())
            throw new AccessException("Argument " + index + " is missing", 330);
        try {
            return (T) args.get(index);
        } catch (ClassCastException e) {
            throw new AccessException(e);
        }
    }

    private static Number num(List<Object> args, int index) throws AccessException {
        if (index >= args.size())
            throw new AccessException("Argument " + index + " is missing", 330);
        Object num = args.get(index);
        if (!(num instanceof Number))
            throw new AccessException("Argument " + index + " is not an instance of number", 340);
        return (Number) num;
    }

    private static class Undeclared {
        private static final Object UNDECLARED = new Undeclared();
    }

    public static NameSpace buildIns() {
        return BUILD_INS;
    }

    final @Nonnull Map<String, Object> variables = new HashMap<>();

    public void declare(@Nonnull String name, @Nonnull Object value) {
        variables.put(name, value);
    }

    public Object get(@Nonnull String name) {
        Object variable = variables.get(name);
        if (variable == null) return Undeclared.UNDECLARED;
        return variable;
    }
}
