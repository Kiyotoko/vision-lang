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

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.vision.instruction.Instruction;
import org.objectweb.asm.ClassWriter;

public class ByteCodeGenerator implements Opcodes {

    public byte[] generateBytecode(VisionParseListener listener, String name) {
        ClassWriter writer = new ClassWriter(0);
        writer.visit(52, ACC_PUBLIC + ACC_SUPER, name, null, "java/lang/Object", null);
        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC + ACC_STATIC, "main",
                "([Ljava/lang/String;)V", null, null);
        int variables = listener.getVariables().size();
        int maxStack = listener.getInstructions().size();
        for (Instruction instruction : listener.getInstructions()) {
            instruction.apply(visitor);
        }
        visitor.visitInsn(RETURN);
        visitor.visitMaxs(maxStack, variables);
        visitor.visitEnd();
        writer.visitEnd();

        return writer.toByteArray();
    }
}
