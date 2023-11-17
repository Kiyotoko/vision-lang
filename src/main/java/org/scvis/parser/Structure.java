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
import java.util.function.Function;

public class Structure implements Accessible {

    private final @Nonnull NameSpace nameSpace = new NameSpace();

    private static void ae(String name) throws AccessException {
        throw new AccessException("Can not access " + name + ", field missing", 310);
    }

    @Nonnull
    @Override
    public Object access(@Nonnull String name) throws AccessException {
        Object present = nameSpace.get(name);
        if (present == null) ae(name);
        return present;
    }

    @Override
    public void mod(@Nonnull String name, @Nonnull Object value) throws AccessException {
        Object present = nameSpace.get(name);
        if (present == null) ae(name);
        nameSpace.declare(name, value);
    }

    @Override
    public void mod(@Nonnull String name, @Nonnull Function<Object, Object> function) throws AccessException {
        Object present = nameSpace.get(name);
        if (present == null) ae(name);
        nameSpace.declare(name, function.apply(present));
    }
}
