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

/**
 * Brackets are a type of value that wraps one or more expressions.
 *
 * @author karlz
 * @see org.scvis.parser.Value
 */
public class Brackets implements Value<Object> {

    private final @Nonnull Object value;

    /**
     * Creates a new brackets object from a list of operators and tokens. The list of tokens must contain the list of
     * tokens.
     *
     * @param operators the list of operators
     * @param tokens    the list of tokens
     */
    public Brackets(@Nonnull List<Operator> operators, @Nonnull List<Token> tokens) {
        List<Value<?>> values = new TokenEvaluator(operators, tokens).evaluate();
        if (values.size() != 1) {
            throw new EvaluationException("Brackets wrap one effective value, got " + values.size());
        }
        this.value = values.get(0).get();
    }

    @Nonnull
    @Override
    public Object get() {
        return value;
    }
}
