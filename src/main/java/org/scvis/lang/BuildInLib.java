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

import org.scvis.ScVis;
import org.scvis.math.MathLib;
import org.scvis.parser.ImportLoader;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public class BuildInLib extends Library {

    private final ImportLoader loader = new ImportLoader() {
        private final Map<String, Supplier<Namespace>> supplier = Map.of("math", MathLib::new);

        @Override
        public boolean exist(String name) {
            return supplier.containsKey(name);
        }

        @Override
        public Set<String> libs() {
            return supplier.keySet();
        }

        @Override
        public Namespace load(String name) {
            if (!exist(name)) throw new InternalError(name);
            return supplier.get(name).get();
        }

        @Override
        public Namespace importInto(Namespace into, Object src) {
            String name = ScVis.asString(src);
            Namespace namespace = load(name);
            into.set(name, namespace);
            return namespace;
        }
    };

    public BuildInLib() {
        set("range", (Callable) args -> new Iterable<Long>() {
            private final long sta = ScVis.asInt(ScVis.getArg(args, 0));
            private final long ste = ScVis.asInt(ScVis.getArg(args, 1));
            private final long sto = ScVis.asInt(ScVis.getArg(args, 2));
            @Nonnull
            @Override
            public Iterator<Long> iterator() {
                return new Iterator<>() {
                    long cur = sta;
                    @Override
                    public boolean hasNext() {
                        return cur < sto;
                    }

                    @Override
                    public Long next() {
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
        set("type", (Callable) args -> (ScVis.getArg(args, 0)).getClass().getSimpleName());
        set("str", (Callable) args -> (ScVis.getArg(args, 0)).toString());
        set("int", (Callable) args -> ScVis.asInt((ScVis.getArg(args, 0))));
        set("float", (Callable) args -> ScVis.asFloat((ScVis.getArg(args, 0))));
        set("print", (Callable) args -> {
            for (Object arg : args) System.out.print(arg + " ");
            System.out.println();
            return args;
        });
        set("import", (Callable) args -> loader.importInto(this, ScVis.getArg(args, 0)));
    }
}
