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

import org.scvis.lang.Namespace;
import org.scvis.parser.*;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

public class ScVis {

    private ScVis() {
        throw new UnsupportedOperationException();
    }

    @CheckReturnValue
    @Nonnull
    public static List<Object> interpret(@Nonnull Namespace nameSpace, @Nonnull String line)
            throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = new TokenParser(nameSpace);
        parser.tokenize(line);
        return new TokenEvaluator(nameSpace, parser.getOperators(), parser.getTokens()).evaluate();
    }

    @CheckReturnValue
    @Nonnull
    public static List<Object> interpretAll(@Nonnull Namespace nameSpace, @Nonnull Iterable<String> lines)
            throws ParsingException, EvaluationException, AccessException {
        TokenParser parser = new TokenParser(nameSpace);
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            parser.tokenize(iterator.next());
            if (iterator.hasNext()) {
                parser.chain();
            }
        }
        return new TokenEvaluator(nameSpace, parser.getOperators(), parser.getTokens()).evaluate();
    }

    public static Number asNumber(Object arg) throws AccessException {
        arg = Namespace.resolved(arg);
        if (arg instanceof Number) return (Number) arg;
        throw new AccessException("Argument is not a number", 309);
    }

    public static Long asInt(Object arg) throws AccessException {
        return asNumber(arg).longValue();
    }

    public static Double asFloat(Object arg) throws AccessException {
        return asNumber(arg).doubleValue();
    }

    public static String asString(Object arg) throws AccessException {
        arg = Namespace.resolved(arg);
        if (arg instanceof String) return (String) arg;
        throw new AccessException("Argument is not a string", 309);
    }

    public static Iterable<?> asIterable(Object arg) throws AccessException {
        arg = Namespace.resolved(arg);
        if (arg instanceof Iterable) return (Iterable<?>) arg;
        throw new AccessException("Argument is not iterable", 309);
    }

    public static <T> T asType(Object arg, Class<T> reflection) throws AccessException {
        arg = Namespace.resolved(arg);
        if (reflection.isInstance(arg)) return reflection.cast(arg);
        throw new AccessException("Argument is not an instance", 310);
    }

    public static Object getArg(List<Object> args, int index) throws AccessException {
        if (index < 0 || index >= args.size()) throw new AccessException("Argument outside range", 311);
        return args.get(index);
    }
}
