package io.scvis.math;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Arrays;

public class Matrix {

    private final @Nonnull double[][] entries;

    private final int m;
    private final int n;

    public Matrix(int m, int n) {
        if (m < 1 || n < 1)
            throw new ArithmeticException("Dimensions must be larger than 0, got m = " + m + " and n = " + n);
        this.entries = new double[m][n];
        this.m = m;
        this.n = n;
    }

    public Matrix(@Nonnull double[][] entries) {
        this.entries = entries;
        this.m = entries.length;
        this.n = entries[0].length;
    }

    @CheckReturnValue
    @Nonnull
    public Matrix add(@Nonnull Matrix other) {
        if (m != other.m && n != other.n) throw new ArithmeticException();
        Matrix created = new Matrix(m, n);
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                created.entries[x][y] = entries[x][y] + other.entries[x][y];
            }
        }
        return created;
    }

    @CheckReturnValue
    @Nonnull
    public Matrix subtract(@Nonnull Matrix other) {
        if (m != other.m && n != other.n) throw new ArithmeticException();
        Matrix created = new Matrix(m, n);
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                created.entries[x][y] = entries[x][y] - other.entries[x][y];
            }
        }
        return created;
    }

    @CheckReturnValue
    @Nonnull
    public Matrix scale(double scale) {
        Matrix created = new Matrix(m, n);
        for (int x = 0; x < m; x++)
            for (int y = 0; y < n; y++)
                created.entries[m][n] = entries[m][n] * scale;
        return created;
    }

    @CheckReturnValue
    @Nonnull
    public Matrix multiply(@Nonnull Matrix other) {
        if (m != other.n) throw new ArithmeticException();
        Matrix created = new Matrix(n, other.m);
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                double sum = 0;
                for (int i = 0; i < m; i++) {
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
        for (int x = 0; x < m; x++) {
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
        return m;
    }

    @CheckReturnValue
    public int getRows() {
        return n;
    }
}
