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
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.scvis.parser.Callable.num;
import static org.scvis.parser.Callable.obj;

public class BuildInLib extends NameSpace {

    public BuildInLib() {
        set("range", (Callable) args -> new Iterable<Integer>() {
            private final int sta = num(args, 0).intValue();
            private final int ste = num(args, 1).intValue();
            private final int sto = num(args, 2).intValue();
            @Nonnull
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<>() {
                    int cur = sta;
                    @Override
                    public boolean hasNext() {
                        return cur < sto;
                    }

                    @Override
                    public Integer next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        try {
                            return cur;
                        } finally {
                            cur += ste;
                        }
                    }
                };
            }
        });
        set("type", (Callable) args -> resolved(obj(args, 0)).getClass().getSimpleName());
        set("str", (Callable) args -> resolved(obj(args, 0)).toString());
        set("print", (Callable) args -> {
            for (Object arg : args) System.out.print(arg + " ");
            System.out.println();
            return args;
        });
    }
}
