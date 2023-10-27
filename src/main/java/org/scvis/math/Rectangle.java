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

import javax.annotation.Nonnull;

public class Rectangle implements Border2D<Rectangle> {

    private final double minX;
    private final double minY;
    private final double maxX;
    private final double maxY;

    public Rectangle(double startX, double startY, double width, double height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Width and height must be positive");
        this.minX = startX;
        this.minY = startY;
        this.maxX = startX + width;
        this.maxY = startY + height;
    }

    public Rectangle(double width, double height) {
        this(0, 0, width, height);
    }

    @Override
    public boolean contains(@Nonnull Point2D point2D) {
        return minX <= point2D.getX() && maxX >= point2D.getX() && minY <= point2D.getY() && maxY >= point2D.getY();
    }

    @Override
    public boolean intersects(@Nonnull Rectangle border2D) {
        return false;
    }

    @Nonnull
    @Override
    public Rectangle translate(double x, double y) {
        return new Rectangle(minX + x, minY + y, maxX + x, maxY + y);
    }

    @Nonnull
    @Override
    public Rectangle rotate(@Nonnull Point2D center, double a) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Point2D centroid() {
        return new Point2D(minX + 0.5 * getWidth(), minY + 0.5 * getHeight());
    }

    public double getWidth() {
        return maxX - minX;
    }

    public double getHeight() {
        return maxY - minY;
    }
}
