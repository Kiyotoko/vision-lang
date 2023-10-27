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
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * The <code>BaseOperator</code> class is a definition template for the default operators of values. It contains the
 * definition for addition, subtraction, multiplication, division,
 *
 * @author karlz
 * @see Operator
 */
@Immutable
public class BaseOperator implements Operator {

    public static final Operator ADD = new BaseOperator((a, b) ->
            new NumberValue(a.getDouble() + b.getDouble()), "+", 1);
    public static final Operator SUBTRACT = new BaseOperator((a, b) ->
            new NumberValue(a.getDouble() - b.getDouble()), "-", 1);
    public static final Operator MULTIPLY = new BaseOperator((a, b) ->
            new NumberValue(a.getDouble() * b.getDouble()), "*", 2);
    public static final Operator DIVIDE = new BaseOperator((a, b) ->
            new NumberValue(a.getDouble() / b.getDouble()), "/", 2);
    public static final Operator MOD = new BaseOperator((a, b) ->
            new NumberValue(a.getDouble() % b.getDouble()), "%", 3);
    public static final Operator POW = new BaseOperator((a, b) ->
            new NumberValue(Math.pow(a.getDouble(), b.getDouble())), "^", 4);
    public static final Operator EQU = new BaseOperator((a, b) -> new NumberValue(a.get().equals(b.get()) ? 1 : 0),
            "=", 0);

    private final @Nonnull BiFunction<Value<?>, Value<?>, Value<?>> function;
    private final @Nonnull String representation;
    private final int priority;
    private final boolean sign;

    /**
     * Creates a new base operator with a binary operator
     *
     * @param function       the binary operator
     * @param representation the string that represents this operator
     * @param priority       the priority
     */
    public BaseOperator(@Nonnull BinaryOperator<Value<?>> function, @Nonnull String representation, int priority) {
        this.function = function;
        this.representation = representation;
        this.priority = priority;
        this.sign = priority == 1;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public Value<?> evaluate(@Nonnull Value<?> left, @Nonnull Value<?> right) {
        return function.apply(left, right);
    }

    @CheckReturnValue
    @Override
    public int priority() {
        return priority;
    }

    @CheckReturnValue
    @Override
    public boolean isSign() {
        return sign;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public String toString() {
        return representation;
    }
}
