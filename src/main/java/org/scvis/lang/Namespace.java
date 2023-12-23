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

package org.scvis.lang;

import org.scvis.parser.AccessBiFunction;
import org.scvis.parser.AccessException;
import org.scvis.parser.Accessible;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.HashMap;
import java.util.Map;

public class Namespace implements Accessible {

    @CheckReturnValue
    @Nonnull
    public static Object resolved(@Nonnull Object obj) throws AccessException {
        if (obj instanceof Resolvable)
            return ((Resolvable) obj).resolve();
        return obj;
    }

    @CheckReturnValue
    @Nonnull
    public static Resolvable unresolved(@Nonnull Object obj) throws AccessException {
        if (obj instanceof Resolvable)
            return (Resolvable) obj;
        throw new AccessException("", 370);
    }

    @Immutable
    public class Unresolved implements Resolvable {
        private final @Nonnull String name;

        Unresolved(@Nonnull String name) {
            this.name = name;
        }

        @CheckReturnValue
        @Nonnull
        @Override
        public Object resolve() throws AccessException {
            Object value = variables.get(name);
            if (value == null) throw new AccessException("No value for " + name, 380);
            return value;
        }

        @CheckReturnValue
        @Nonnull
        @Override
        public String source() {
            return name;
        }
    }

    final @Nonnull Map<String, Object> variables = new HashMap<>();

    @Override
    public void set(@Nonnull String name, @Nonnull Object value) {
        variables.put(name, value);
    }

    public void mod(@Nonnull String name, @Nonnull Object value, AccessBiFunction<Object, Object, Object> function)
            throws AccessException {
        Object present = variables.get(name);
        if (present == null)
            throw new AccessException("", 399);
        set(name, function.apply(present, value));
    }

    @CheckReturnValue
    @Nonnull
    public Unresolved get(@Nonnull String name) {
        return new Unresolved(name);
    }
}
