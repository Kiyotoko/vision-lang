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
