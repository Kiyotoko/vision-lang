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
import javax.annotation.Nullable;

public class Triangle implements Border3D<Triangle> {

    private final @Nonnull Point3D p0;
    private final @Nonnull Point3D p1;
    private final @Nonnull Point3D p2;

    public Triangle(@Nonnull Point3D p0, @Nonnull Point3D p1, @Nonnull Point3D p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    @CheckReturnValue
    @Override
    public boolean contains(@Nonnull Point3D p) {
        Point3D ab = p1.subtract(p0);
        Point3D ac = p2.subtract(p0);
        Point3D pp = p.subtract(p0);
        double[] solutions = Equations.solve(ab.getX(), ac.getX(), pp.getX(), ab.getY(), ac.getY(), pp.getY());
        if (solutions.length == 0) return false;
        double x = solutions[0];
        double y = solutions[1];
        if (ab.getZ() * x + ac.getZ() * y == pp.getZ()) {
            return x >= 0 && y >= 0 && x + y <= 1;
        }
        return false;
    }

    @CheckReturnValue
    @Override
    public boolean intersects(@Nonnull Triangle tri) {
        Point3D ab = p1.subtract(p0);
        Point3D ac = p2.subtract(p0);
        Point3D de = tri.p1.subtract(tri.p0); // line vector

        Point3D df = tri.p2.subtract(tri.p0); // check after

        double[] solution = Equations.solve(
                new double[][]{{ab.getX(), ac.getX(), -de.getX()}, {ab.getY(), ac.getY(), -de.getY()},
                        {ab.getZ(), ac.getZ(), -de.getZ()}},
                new double[]{tri.p0.getX() - p0.getX(), tri.p0.getY() - p0.getY(), tri.p0.getZ() - p0.getZ()});

        throw new UnsupportedOperationException();
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public Triangle translate(double x, double y, double z) {
        return new Triangle(p0.add(x, y, z), p1.add(x, y, z), p2.add(x, y, z));
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public Triangle rotate(@Nonnull Point3D center, double a, double b) {
        throw new UnsupportedOperationException();
    }

    @CheckReturnValue
    @Nonnull
    @Override
    public Point3D centroid() {
        return new Point3D((p0.getX() + p1.getX() + p2.getX()) / 3, (p0.getY() + p1.getY() + p2.getY()) / 3,
                (p0.getZ() + p1.getZ() + p2.getZ()) / 3);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj instanceof Triangle) {
            Triangle tri = (Triangle) obj;
            return p0.equals(tri.p0) && p1.equals(tri.p1) || p2.equals(tri.p2);
        }
        return false;
    }
}
