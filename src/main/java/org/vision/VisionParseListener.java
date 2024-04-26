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

package org.vision;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.vision.instruction.CallInstruction;
import org.vision.instruction.DeclarationInstruction;
import org.vision.instruction.Instruction;

import java.util.*;

public class VisionParseListener extends VisionBaseListener {

    private final Map<String, Variable> variables = new HashMap<>();
    private final Queue<Instruction> instructions = new ArrayDeque<>();

    @Override
    public void exitCall(VisionParser.CallContext ctx) {
        TerminalNode name = ctx.NAME();

        Variable variable = variables.get(name.getText());
        if (variable == null) {
            System.err.printf("Not yet declared: %s %n", name.getText());
        } else {
            instructions.add(new CallInstruction(variable));
            log(name, variable);
        }
    }

    @Override
    public void exitDeclaration(VisionParser.DeclarationContext ctx) {
        TerminalNode name = ctx.NAME();

        VisionParser.ValueContext context = ctx.value();
        int type = context.getStart().getType();
        int index = variables.size();
        Variable variable = new Variable(index, type, context.getText());
        variables.put(name.getText(), variable);
        instructions.add(new DeclarationInstruction(variable));
        log(name, variable);
    }

    private void log(TerminalNode name, Variable variable) {
        int line = name.getSymbol().getLine();
        String format = "Statement: '%s' -> '%s' at line '%s'.'\n";
        System.out.printf(format, variable.getIndex(), variable.getValue(), line);
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public Queue<Instruction> getInstructions() {
        return instructions;
    }
}
