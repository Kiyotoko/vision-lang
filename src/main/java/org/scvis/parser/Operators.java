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

import org.scvis.ScVis;

import javax.annotation.Nonnull;

public final class Operators {

    private Operators() {
        throw new UnsupportedOperationException();
    }

    public abstract static class Sign implements Operator {

        @Override
        public int priority() {
            return 20;
        }

        public static final @Nonnull Operator ADD = new Sign() {
            @Override
            public Object apply(Object left, Object right) {
                return ScVis.asFloat(left) + ScVis.asFloat(right);
            }
        };

        public static final @Nonnull Operator SUB = new Sign() {
            @Override
            public Object apply(Object left, Object right) {
                return ScVis.asFloat(left) - ScVis.asFloat(right);
            }
        };
    }

    public static final @Nonnull Operator MUL = new Operator() {
        @Override
        public int priority() {
            return 30;
        }

        @Override
        public Object apply(Object left, Object right) {
            return ScVis.asFloat(left) * ScVis.asFloat(right);
        }
    };

    public static final @Nonnull Operator DIV = new Operator() {
        @Override
        public int priority() {
            return 30;
        }

        @Override
        public Object apply(Object left, Object right) {
            return ScVis.asFloat(left) / ScVis.asFloat(right);
        }
    };

    public static final @Nonnull Operator MOD = new Operator() {
        @Override
        public int priority() {
            return 40;
        }

        @Override
        public Object apply(Object left, Object right) {
            return ScVis.asFloat(left) % ScVis.asFloat(right);
        }
    };

    public static final @Nonnull Operator POW = new Operator() {
        @Override
        public int priority() {
            return 40;
        }

        @Override
        public Object apply(Object left, Object right) {
            return Math.pow(ScVis.asFloat(left), ScVis.asFloat(right));
        }
    };

    public static final Operator SEPARATOR = new Operator() {
        @Nonnull
        @Override
        public Object apply(@Nonnull Object left, @Nonnull Object right) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int priority() {
            return -10;
        }
    };

    public static final @Nonnull Operator DECLARE = new Operator() {
        @Override
        public int priority() {
            return 10;
        }

        @Override
        public Object apply(Object left, Object right) {
            return ScVis.asLabel(left).setResource(right);
        }
    };

    public static final @Nonnull Operator ADD_TO = new Operator() {
        @Override
        public int priority() {
            return 10;
        }

        @Override
        public Object apply(Object left, Object right) {
            Label label = ScVis.asLabel(left);
            return label.setResource(ScVis.asFloat(label.getResource()) + ScVis.asFloat(right));
        }
    };

    public static final @Nonnull Operator SUB_TO = new Operator() {
        @Override
        public int priority() {
            return 10;
        }

        @Override
        public Object apply(Object left, Object right) {
            Label label = ScVis.asLabel(left);
            return label.setResource(ScVis.asFloat(label.getResource()) + ScVis.asFloat(right));
        }
    };

    public static final @Nonnull Operator MUL_TO = new Operator() {
        @Override
        public int priority() {
            return 10;
        }

        @Override
        public Object apply(Object left, Object right) {
            Label label = ScVis.asLabel(left);
            return label.setResource(ScVis.asFloat(label.getResource()) + ScVis.asFloat(right));
        }
    };

    public static final @Nonnull Operator DIV_TO = new Operator() {
        @Override
        public int priority() {
            return 10;
        }

        @Override
        public Object apply(Object left, Object right) {
            Label label = ScVis.asLabel(left);
            return label.setResource(ScVis.asFloat(label.getResource()) + ScVis.asFloat(right));
        }
    };


    public static final @Nonnull Operator IS_EQUALS = new Operator() {
        @Override
        public int priority() {
            return 15;
        }

        @Override
        public Object apply(Object left, Object right) {
            return left.equals(right);
        }
    };

    public static final @Nonnull Operator NOT_EQUALS = new Operator() {
        @Override
        public int priority() {
            return 15;
        }

        @Override
        public Object apply(Object left, Object right) {
            return !left.equals(right);
        }
    };

}
