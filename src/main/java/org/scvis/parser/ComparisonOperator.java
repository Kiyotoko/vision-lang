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

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static org.scvis.lang.Namespace.resolved;

@Immutable
public class ComparisonOperator implements Operator {

    public static final @Nonnull Operator IS_EQUALS = new ComparisonOperator((a, b) -> resolved(a).equals(resolved(b)));

    public static final @Nonnull Operator NOT_EQUALS =
            new ComparisonOperator((a, b) -> !resolved(a).equals(resolved(b)));

    private final @Nonnull AccessBiFunction<Object, Object, Boolean> predicate;

    protected ComparisonOperator(@Nonnull AccessBiFunction<Object, Object, Boolean> predicate) {
        this.predicate = predicate;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public Boolean evaluate(@Nonnull Object left, @Nonnull Object right) throws AccessException {
        return predicate.apply(left, right);
    }

    @Override
    public int priority() {
        return 15;
    }
}
