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

import org.scvis.ScVisException;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.function.BinaryOperator;

/**
 * An operator always evaluates two values and returns a new value. Therefore, <char>!</char> is not an operator,
 * because it only evaluates one value. Each operator can also be a sign. A sign can take <code>0</code> as a left value
 * if no left value is given. If it can not get a left or right value, a {@link ScVisException} is thrown.
 *
 * @author karlz
 * @see Comparable
 */
public interface Operator extends Comparable<Operator>, BinaryOperator<Object> {

    /**
     * The priority is the sequence, in which the values are evaluated.
     *
     * @return the priority
     */
    @CheckReturnValue
    int priority();

    @Override
    default int compareTo(@Nonnull Operator o) {
        return o.priority() - priority();
    }
}
