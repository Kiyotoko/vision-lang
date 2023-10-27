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

package org.scvis.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestEquations {

    @Test
    void solveMatrix() {
        assertArrayEquals(new double[]{1, -1},
                Equations.solve(Vector.toMatrix(new Vector(2.0, 3.0), new Vector(5.0, 3.0)), new Vector(-1.0, 2.0))
                        .getElements());
    }

    @Test
    void solveArray() {
        assertArrayEquals(new double[]{1, -1}, Equations.solve(new double[][]{{2, 3}, {5, 3}}, new double[]{-1, 2}));
    }

    @Test
    void solveABCDEF() {
        assertArrayEquals(new double[]{1, -1}, Equations.solve(2, 3, -1, 5, 3, 2));
        assertArrayEquals(new double[]{5, 3}, Equations.solve(1, 3, 14, 4, -2, 14));
    }

    @Test
    void solvePQ() {
        assertArrayEquals(new double[]{3, -8}, Equations.solve(5, -24));
    }

    @Test
    void solveABC() {
        assertEquals(0, Equations.solve(2, 4, 5).length);

        double[] solutions = Equations.solve(2, 4, -16);
        double x0 = solutions[0];
        assertEquals(0, 2 * x0 * x0 + 4 * x0 - 16);
        double x1 = solutions[1];
        assertEquals(0, 2 * x1 * x1 + 4 * x1 - 16);
    }
}