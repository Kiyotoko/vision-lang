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
 *
 */

package org.scvis.parser;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static org.scvis.parser.Operator.num;

/**
 * The <code>BaseOperator</code> class is a definition template for the default operators of values. It contains the
 * definition for addition, subtraction, multiplication, division,
 *
 * @author karlz
 * @see Operator
 */
@Immutable
public class BinaryOperator implements Operator {

    public static final @Nonnull Operator MUL =
            new BinaryOperator((a, b) -> num(a).doubleValue() * num(b).doubleValue(), '*', 30);
    public static final @Nonnull Operator DIV =
            new BinaryOperator((a, b) -> num(a).doubleValue() / num(b).doubleValue(), '/', 30);
    public static final @Nonnull Operator MOD =
            new BinaryOperator((a, b) -> num(a).doubleValue() % num(b).doubleValue(), '%', 40);
    public static final @Nonnull Operator POW =
            new BinaryOperator((a, b) -> Math.pow(num(a).doubleValue(), num(b).doubleValue()), '^', 60);

    @Immutable
    public static class OperatorAndSign extends BinaryOperator implements Sign {
        public static final @Nonnull Operator ADD =
                new OperatorAndSign((a, b) -> num(a).doubleValue() + num(b).doubleValue(), '+', 20);
        public static final @Nonnull Operator SUB =
                new OperatorAndSign((a, b) -> num(a).doubleValue() - num(b).doubleValue(), '-', 20);

        public OperatorAndSign(@Nonnull AccessBiFunction<Object, Object, Object> function, char representation,
                               int priority) {
            super(function, representation, priority);
        }
    }

    private final @Nonnull AccessBiFunction<Object, Object, Object> function;
    private final @Nonnull String representation;
    private final int priority;

    /**
     * Creates a new base operator with a binary operator
     *
     * @param function       the binary operator
     * @param representation the string that represents this operator
     * @param priority       the priority
     */
    protected BinaryOperator(@Nonnull AccessBiFunction<Object, Object, Object> function, char representation,
                             int priority) {
        this.function = function;
        this.representation = "" + representation;
        this.priority = priority;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public Object evaluate(@Nonnull Object left, @Nonnull Object right) throws AccessException {
        return function.apply(left, right);
    }

    @CheckReturnValue
    @Override
    public int priority() {
        return priority;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public String toString() {
        return representation;
    }
}
