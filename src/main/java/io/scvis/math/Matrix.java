package io.scvis.math;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class Matrix {

    private final @Nonnull double[][] data;

    private final int m;
    private final int n;

    public Matrix(int m, int n) {
        this.data = new double[m][n];
        this.m = m;
        this.n = n;
    }

    public Matrix(@Nonnull double[][] data) {
        this.data = data;
        this.m = data.length;
        this.n = data[0].length;
    }

    @Nonnull
    public Matrix add(@Nonnull Matrix other) {
        if (m != other.m && n != other.n) throw new ArithmeticException();
        Matrix created = new Matrix(m, n);
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                created.data[x][y] = data[x][y] + other.data[x][y];
            }
        }
        return created;
    }

    @Nonnull
    public Matrix subtract(@Nonnull Matrix other) {
        if (m != other.m && n != other.n) throw new ArithmeticException();
        Matrix created = new Matrix(m, n);
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                created.data[x][y] = data[x][y] - other.data[x][y];
            }
        }
        return created;
    }

    @Nonnull
    public Matrix multiply(@Nonnull Matrix other) {
        if (m != other.n) throw new ArithmeticException();
        Matrix created = new Matrix(n, other.m);
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                double sum = 0;
                for (int i = 0; i < m; i++) {
                    sum += data[i][y] * other.data[x][i];
                }
                created.data[x][y] = sum;
            }
        }
        return created;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("[");
        for (int x = 0; x < m; x++) {
            build.append("\n\t").append(Arrays.toString(data[x]));
        }
        build.append("\n]");
        return build.toString();
    }

    public int getColumns() {
        return m;
    }

    public int getRows() {
        return n;
    }
}
