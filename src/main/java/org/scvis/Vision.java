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

package org.scvis;

import org.scvis.lang.Label;
import org.scvis.lang.Namespace;
import org.scvis.interpreter.*;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.List;

public class Vision {

    private Vision() {
        throw new UnsupportedOperationException();
    }

    @CheckReturnValue
    @Nonnull
    public static List<Object> interpret(@Nonnull Namespace namespace, @Nonnull String line) {
        TokenParser parser = new TokenParser(namespace);
        parser.parse(line);
        return new TokenEvaluator(namespace, parser.getStatement()).evaluate();
    }

    @CheckReturnValue
    @Nonnull
    private static ClassCastException cce(@Nonnull Object arg, String type) {
        return new ClassCastException("Argument '" + arg + "' could not be cast to " + type);
    }

    public static Number asNumber(@Nonnull Object arg){
        if (arg instanceof Number) return (Number) arg;
        throw cce(arg, "number");
    }

    public static Long asInt(@Nonnull Object arg) {
        return asNumber(arg).longValue();
    }

    public static Double asFloat(@Nonnull Object arg) {
        return asNumber(arg).doubleValue();
    }

    public static String asString(@Nonnull Object arg) {
        if (arg instanceof String) return (String) arg;
        throw cce(arg, "string");
    }

    public static Label asLabel(@Nonnull Object arg) {
        if (arg instanceof Label) return (Label) arg;
        throw cce(arg, "label");
    }

    @SuppressWarnings("unused")
    public static Iterable<?> asIterable(@Nonnull Object arg) {
        if (arg instanceof Iterable) return (Iterable<?>) arg;
        throw cce(arg,"iterable");
    }

    public static Object getArg(@Nonnull List<Object> args, int index) {
        if (index < 0 || index >= args.size()) throw new IndexOutOfBoundsException("Argument outside range");
        return args.get(index);
    }
}
