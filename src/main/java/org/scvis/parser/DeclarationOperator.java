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

import static org.scvis.parser.NameSpace.unresolved;
import static org.scvis.parser.NameSpace.resolved;

@Immutable
public class DeclarationOperator implements Operator {

    public static final @Nonnull DeclarationOperator
            DECLARE = new DeclarationOperator((a, b) -> ((n) -> n.declare(unresolved(a).source(), resolved(b))));
    public static final @Nonnull DeclarationOperator ADD_TO =
            new DeclarationOperator((a, b) -> ((n) -> n.changeBy(unresolved(a).source(), b,
                    BinaryOperator.OperatorAndSign.ADD::evaluate)));
    public static final @Nonnull DeclarationOperator SUB_TO =
            new DeclarationOperator((a, b) -> ((n) -> n.changeBy(unresolved(a).source(), b,
                    BinaryOperator.OperatorAndSign.SUB::evaluate)));
    public static final @Nonnull DeclarationOperator MUL_TO =
            new DeclarationOperator((a, b) -> ((n) -> n.changeBy(unresolved(a).source(), b,
                    BinaryOperator.MUL::evaluate)));
    public static final @Nonnull DeclarationOperator DIV_TO =
            new DeclarationOperator((a, b) -> ((n) -> n.changeBy(unresolved(a).source(), b,
                    BinaryOperator.DIV::evaluate)));

    private final @Nonnull AccessBiFunction<Object, Object, AccessOperator> function;

    protected DeclarationOperator(@Nonnull AccessBiFunction<Object, Object, AccessOperator> function) {
        this.function = function;
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public AccessOperator evaluate(@Nonnull Object left, @Nonnull Object right) throws AccessException {
        return function.apply(left, right);
    }

    @CheckReturnValue
    @Override
    public int priority() {
        return 10;
    }
}
