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

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Arrays;

public class Matrix {

    private final @Nonnull double[][] entries;

    private final int columns;
    private final int rows;

    public Matrix(int columns, int rows) {
        if (columns < 1 || rows < 1)
            throw new ArithmeticException("Dimensions must be larger than 0, got m = " + columns + " and n = " + rows);
        this.entries = new double[columns][rows];
        this.columns = columns;
        this.rows = rows;
    }

    public Matrix(@Nonnull double[][] entries) {
        this.entries = entries;
        this.columns = entries.length;
        this.rows = entries[0].length;
    }

    @Nonnull
    public Matrix add(@Nonnull Matrix other) {
        if (columns != other.columns && rows != other.rows) throw new ArithmeticException();
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                entries[x][y] += other.entries[x][y];
            }
        }
        return this;
    }

    @Nonnull
    public Matrix subtract(@Nonnull Matrix other) {
        if (columns != other.columns && rows != other.rows) throw new ArithmeticException();
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                entries[x][y] -= other.entries[x][y];
            }
        }
        return this;
    }

    @Nonnull
    public Matrix scale(double scale) {
        for (int x = 0; x < columns; x++)
            for (int y = 0; y < rows; y++)
                entries[columns][rows] *= scale;
        return this;
    }

    @CheckReturnValue
    @Nonnull
    public Matrix multiply(@Nonnull Matrix other) {
        if (columns != other.rows) throw new ArithmeticException();
        Matrix created = new Matrix(rows, other.columns);
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                double sum = 0;
                for (int i = 0; i < columns; i++) {
                    sum += entries[i][y] * other.entries[x][i];
                }
                created.entries[x][y] = sum;
            }
        }
        return created;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("[");
        for (int x = 0; x < columns; x++) {
            build.append("\n\t").append(Arrays.toString(entries[x]));
        }
        build.append("\n]");
        return build.toString();
    }

    public void setEntry(int c, int r, double v) {
        entries[c][r] = v;
    }

    @CheckReturnValue
    public double getEntry(int c, int r) {
        return entries[c][r];
    }

    @CheckReturnValue
    @Nonnull
    public double[][] getEntries() {
        return entries;
    }

    @CheckReturnValue
    public int getColumns() {
        return columns;
    }

    @CheckReturnValue
    public int getRows() {
        return rows;
    }
}
