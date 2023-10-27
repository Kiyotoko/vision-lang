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

public final class Equations {

    private Equations() {
        throw new UnsupportedOperationException();
    }

    @CheckReturnValue
    @Nonnull
    public static Vector solve(@Nonnull Matrix variables, @Nonnull Vector values) {
        return new Vector(solve(variables.getEntries(), values.getElements()));
    }

    @CheckReturnValue
    @Nonnull
    public static double[] solve(@Nonnull double[][] variables, @Nonnull double[] values) {
        int size = values.length;
        for (int k = 0; k < size; k++) {
            /* find pivot row */
            int max = k;
            for (int i = k + 1; i < size; i++)
                if (Math.abs(variables[i][k]) > Math.abs(variables[max][k]))
                    max = i;

            /* swap row in A matrix */
            double[] temp = variables[k];
            variables[k] = variables[max];
            variables[max] = temp;

            /* swap corresponding values in constants matrix */
            double t = values[k];
            values[k] = values[max];
            values[max] = t;

            /* pivot within A and B */
            for (int i = k + 1; i < size; i++) {
                double factor = variables[i][k] / variables[k][k];
                values[i] -= factor * values[k];
                for (int j = k; j < size; j++)
                    variables[i][j] -= factor * variables[k][j];
            }
        }

        /* back substitution */
        double[] solution = new double[size];
        for (int i = size - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < size; j++)
                sum += variables[i][j] * solution[j];
            solution[i] = (values[i] - sum) / variables[i][i];
        }
        return solution;
    }

    /**
     * Solves an equation in the form of:
     * <p>
     * <code>
     * ax + by = c<br> dx + ey = f
     * </code>
     *
     * @param a the factor ax
     * @param b the factor by
     * @param c the constant c
     * @param d the factor dx
     * @param e the factor ey
     * @param f the constant f
     * @return an array containing x and y, or an empty array if there is no solution
     */
    public static double[] solve(double a, double b, double c, double d, double e, double f) {
        double y = (d * c - a * f) / (d * b - a * e);
        double x = (c - b * y) / a;
        if (a * x + b * y == c && d * x + e * y == f) return new double[]{x, y};
        return new double[]{};
    }

    /**
     * Solves an equation in the form of: <p
     * <code>
     * ax^2 + bx + c = 0
     * </code>
     *
     * @param a the factor a
     * @param b the factor b
     * @param c the constant c
     * @return an array containing all possible different solutions for x
     */
    @CheckReturnValue
    @Nonnull
    public static double[] solve(double a, double b, double c) {
        if (a == 0) return (c == 0 ? new double[]{c} : new double[]{});
        double l = -b / (2 * a);
        double r = Math.sqrt(b * b - 4 * a * c) / (2 * a);
        if (Double.isNaN(r)) return new double[]{};
        if (r == 0) return new double[]{l};
        return new double[]{l + r, l - r};
    }

    /**
     * Solves an equation in the form of:
     * <p>
     * <code>
     * x^2 + px + q = 0
     * </code>
     *
     * @param p the factor p
     * @param q the constant q
     * @return an array containing all possible different solutions for x
     */
    @CheckReturnValue
    @Nonnull
    public static double[] solve(double p, double q) {
        double l = -p / 2;
        double r = Math.sqrt(p * p / 4 - q);
        if (Double.isNaN(r)) return new double[]{};
        if (r == 0) return new double[]{l};
        return new double[]{l + r, l - r};
    }
}
