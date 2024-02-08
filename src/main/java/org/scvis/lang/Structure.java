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

package org.scvis.lang;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Structure {

    Map<String, Integer> labels;

    class Constructor implements Callable {

        @Override
        public Creation call(List<java.lang.Object> args) {
            if (args.size() != labels.size())
                throw new IllegalArgumentException("False number of arguments for constructor");
            return new Creation(args);
        }
    }

    class Creation implements Namespace {
        private final Object[] fields = new Object[labels.size()];

        Creation(Iterable<Object> data) {
            int i = 0;
            for (var v : data) {
                fields[i++]=v;
            }
        }

        @Override
        public void set(@Nonnull String label, @Nonnull Object value) {
            fields[labels.get(label)]=value;
        }

        @Nonnull
        @Override
        public Object get(@Nonnull String label) {
            return fields[labels.get(label)];
        }

        @Nonnull
        @Override
        public Set<String> labels() {
            return labels.keySet();
        }
    }
}
