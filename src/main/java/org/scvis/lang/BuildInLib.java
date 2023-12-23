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
import org.scvis.lang.Callable;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;

import org.scvis.lang.Namespace;
import org.scvis.lang.Statement;

public class BuildInLib extends Namespace {

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
        set("type", (Callable) args -> resolved(ScVis.getArg(args, 0)).getClass().getSimpleName());
        set("str", (Callable) args -> resolved(ScVis.getArg(args, 0)).toString());
        set("int", (Callable) args -> ScVis.asInt(resolved(ScVis.getArg(args, 0))));
        set("float", (Callable) args -> ScVis.asFloat(resolved(ScVis.getArg(args, 0))));
        set("print", (Callable) args -> {
            for (Object arg : args) System.out.print(arg + " ");
            System.out.println();
            return args;
        });
        set("if", (Callable) IfFunction::new);
    }

    public static class IfFunction implements Statement, Introducer {

        Statement statement;

        boolean condition = true;

        public IfFunction(List<Object> args) {
            for (Object arg : args) if (!(arg instanceof Boolean) || (boolean) arg) {
                this.condition = false;
                break;
            }
        }

        @Override
        public void introduce(Statement statement) {
            this.statement = statement;
        }

        @Override
        public void execute(@Nonnull Namespace space) throws AccessException, ParsingException, EvaluationException {
            if (condition) {
                if (statement == null) throw new ParsingException("Statement is null", 195);
                statement.execute(space);
            }
        }
    }
}
