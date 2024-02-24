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

package org.scvis.interpreter;

import org.scvis.Vision;
import org.scvis.lang.Label;
import org.scvis.lang.Library;
import org.scvis.lang.Method;
import org.scvis.lang.Namespace;
import org.scvis.math.MathLib;

import java.util.*;
import java.util.function.Supplier;

public class BuildInLib extends Library {

    private final ImportLoader loader = new ImportLoader() {
        private final Map<String, Supplier<Namespace>> supplier = Map.of("math", MathLib::new);

        @Override
        public boolean exist(String name) {
            return libs().contains(name);
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
            Label label = Vision.asLabel(src);
            Namespace namespace = load(label.getName());
            into.set(label.getName(), namespace);
            return namespace;
        }
    };

    public BuildInLib() {
        set("range", new Method(this) {
            @Override
            public Object apply(List<Object> args) {
                final long sta = Vision.asInt(Vision.getArg(args, 0));
                final long ste = Vision.asInt(Vision.getArg(args, 1));
                final long sto = Vision.asInt(Vision.getArg(args, 2));
                return (Iterable<Long>) () -> new Iterator<>() {
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
        set("type", new Method(this) {
            @Override
            public Object apply(List<Object> args) {
                return Vision.getArg(args, 0).getClass().getSimpleName();
            }
        });
        set("str", new Method(this) {
            @Override
            public Object apply(List<Object> args) {
                return Vision.getArg(args, 0).toString();
            }
        });
        set("int", new Method(this) {
            @Override
            public Object apply(List<Object> args) {
                Object arg = Vision.getArg(args, 0);
                if (arg instanceof String) return Integer.parseInt(arg.toString());
                return Vision.asInt(arg);
            }
        });
        set("float", new Method(this) {
            @Override
            public Object apply(List<Object> args) {
                Object arg = Vision.getArg(args, 0);
                if (arg instanceof String) return Double.parseDouble(arg.toString());
                return Vision.asFloat(arg);
            }
        });
        set("print", new Method(this) {
            @Override
            public Object apply(List<Object> args) {
                for (Object arg : args) System.out.print(arg + " ");
                System.out.println();
                return args;
            }
        });
        set("import", new Method(this) {
            @Override
            public Object apply(List<Object> args) {
                return loader.importInto(BuildInLib.this, Vision.getArg(args, 0));
            }
        });
    }
}
