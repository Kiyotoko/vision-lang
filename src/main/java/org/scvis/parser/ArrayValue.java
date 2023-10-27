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
import java.util.*;

public class ArrayValue implements Value<Value<?>[]> {

    private static final Class<?> ANY = Object.class;

    private final @Nonnull Value<?>[] values;
    private final @Nonnull Class<?> typing;
    private final int size;

    public ArrayValue(Value<?>... values) {
        this.values = values;
        this.size = values.length;
        this.typing = size > 0 ? values[0].getClass() : ANY;
        check();
    }

    public ArrayValue(TokenParser parser) {
        TokenEvaluator evaluator = new TokenEvaluator(parser.getOperators(), parser.getTokens());
        List<Value<?>> list = evaluator.evaluate();

        this.values = list.toArray(new Value[0]);
        this.size = list.size();
        this.typing = size > 0 ? values[0].getClass() : ANY;
        check();
    }

    private void check() {
        for (Value<?> value : get()) {
            if (!(getTyping().isInstance(value)))
                throw new EvaluationException(
                        "Array requires " + getTyping().getSimpleName() + ", got " + value.getClass().getSimpleName());
        }
    }

    @Nonnull
    @Override
    public Value<?>[] get() {
        return values;
    }

    @Nonnull
    public Class<?> getTyping() {
        return typing;
    }

    public int getSize() {
        return size;
    }
}