package io.scvis.math;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;

public class Vector implements Serializable {

    @Nonnull
    private final double[] elements;

    private final int size;

    public Vector(@Nonnull double[] elements) {
        this.elements = elements;
        this.size = elements.length;
    }

    public Vector(int size) {
        this.elements = new double[size];
        this.size = size;
    }

    @CheckReturnValue
    @Nonnull
    public Vector add(@Nonnull Vector another) {
        Vector created = new Vector(size);
        for (int i = 0; i < size; i++) {
            created.elements[i] = elements[i] + another.elements[i];
        }
        return created;
    }

    @CheckReturnValue
    @Nonnull
    public Vector subtract(@Nonnull Vector another) {
        Vector created = new Vector(size);
        for (int i = 0; i < size; i++) {
            created.elements[i] = elements[i] - another.elements[i];
        }
        return created;
    }

    @CheckReturnValue
    @Nonnull
    public Vector difference(@Nonnull Vector another) {
        return subtract(another);
    }

    @CheckReturnValue
    public double distance(@Nonnull Vector another) {
        double distance = 0;
        for (int i = 0; i < size; i++) {
            double dif = elements[i] - another.elements[i];
            distance += dif * dif;
        }
        return Math.sqrt(distance);
    }

    @CheckReturnValue
    public double magnitude() {
        double magnitude = 0;
        for (double element : getElements())
            magnitude += element * element;
        return Math.sqrt(magnitude);
    }

    @CheckReturnValue
    public double length() {
        return magnitude();
    }

    @CheckReturnValue
    @Nonnull
    public Vector normalized() {
        double mag = magnitude();
        Vector created = new Vector(size);
        if (mag != 0) {
            for (int i = 0; i < size; i++)
                created.elements[i] = elements[i] / mag;
        }
        return created;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    @CheckReturnValue
    @Nonnull
    public double[] getElements() {
        return elements;
    }

    public int getSize() {
        return size;
    }

    transient int hash = 0;

    @Override
    public int hashCode() {
        if (hash != 0) return hash;
        double code = 43;
        for (double element : elements) {
            code = code * size + element;
        }
        hash = (int) code;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Vector))
            return false;

        Vector cast = (Vector) obj;
        if (size != cast.size) return false;
        for (int i = 0; i < size; i++) {
            if (elements[i] != cast.elements[i])
                return false;
        }
        return true;
    }
}
