package org.scvis.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TestVector {

    @Test
    void toMatrix() {
        System.out.println(Vector.toMatrix(new Point3D(1, 2, 3).toVector(), new Point3D(4, 5, 6).toVector(),
                new Point3D(7, 8, 9).toVector()));
    }

    @ParameterizedTest
    @CsvSource("1,2,3,4,5,6,5,7,9")
    void add(double ax, double ay, double az, double bx, double by, double bz, double cx, double cy, double cz) {
        Vector a = new Vector(ax, ay, az);
        Vector b = new Vector(bx, by, bz);

        Assertions.assertEquals(new Vector(cx, cy, cz), a.add(b));
    }

    @ParameterizedTest
    @CsvSource("4,5,6,1,2,3,3,3,3")
    void subtract(double ax, double ay, double az, double bx, double by, double bz, double cx, double cy, double cz) {
        Vector a = new Vector(ax, ay, az);
        Vector b = new Vector(bx, by, bz);

        Assertions.assertEquals(new Vector(cx, cy, cz), a.subtract(b));
        Assertions.assertEquals(new Vector(cx, cy, cz), a.difference(b));
    }

    @ParameterizedTest
    @CsvSource("1,0,0,1,0,0")
    void normalized(double ax, double ay, double az, double bx, double by, double bz) {
        Assertions.assertEquals(new Vector(bx, by, bz),
                new Vector(ax, ay, az).normalized());
    }

    @Test
    void size() {
        Assertions.assertEquals(5, new Vector(1, 2, 3, 4, 5).getSize());
    }

    @Test
    void length() {
        Assertions.assertEquals(Math.sqrt(2), new Vector(1, 1).length());
    }

    @Test
    void distance() {
        Assertions.assertEquals(Math.sqrt(2), new Vector(1, 1)
                .distance(new Vector(2, 2)));
    }
}
