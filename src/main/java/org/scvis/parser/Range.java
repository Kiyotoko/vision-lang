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
import javax.annotation.concurrent.Immutable;
import java.util.Iterator;

@Immutable
public class Range implements Iterable<Double> {
    private final double start;
    private final double step;
    private final double stop;

    public Range(double start, double step, double stop) {
        this.start = start;
        this.step = step;
        this.stop = stop;
    }

    public Range(double stop) {
        this(0, Math.signum(stop), stop);
    }

    private class RangeIterator implements Iterator<Double> {
        private double current = start;

        @Override
        public boolean hasNext() {
            return current < stop;
        }

        @Override
        public Double next() {
            return current += step;
        }
    }

    public boolean contains(double value) {
        if (value < start || value >= stop) return false;
        return (value - start) % step == 0;
    }

    @Nonnull
    @Override
    public Iterator<Double> iterator() {
        return new RangeIterator();
    }
}
