package org.scvis.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TestVector {

    @ParameterizedTest
    @CsvSource("1,2,3,4,5,6,5,7,9")
    void add(double ax, double ay, double az, double bx, double by, double bz, double cx, double cy, double cz) {
        Vector a = new Vector(new double[]{ax, ay, az});
        Vector b = new Vector(new double[]{bx, by, bz});

        Assertions.assertEquals(new Vector(new double[]{cx, cy, cz}), a.add(b));
    }

    @ParameterizedTest
    @CsvSource("4,5,6,1,2,3,3,3,3")
    void subtract(double ax, double ay, double az, double bx, double by, double bz, double cx, double cy, double cz) {
        Vector a = new Vector(new double[]{ax, ay, az});
        Vector b = new Vector(new double[]{bx, by, bz});

        Assertions.assertEquals(new Vector(new double[]{cx, cy, cz}), a.subtract(b));
        Assertions.assertEquals(new Vector(new double[]{cx, cy, cz}), a.difference(b));
    }

    @ParameterizedTest
    @CsvSource("1,0,0,1,0,0")
    void normalized(double ax, double ay, double az, double bx, double by, double bz) {
        Assertions.assertEquals(new Vector(new double[]{bx, by, bz}),
                new Vector(new double[]{ax, ay, az}).normalized());
    }

    @Test
    void size() {
        Assertions.assertEquals(5, new Vector(new double[]{1, 2, 3, 4, 5}).getSize());
    }

    @Test
    void length() {
        Assertions.assertEquals(Math.sqrt(2), new Vector(new double[]{1, 1}).length());
    }

    @Test
    void distance() {
        Assertions.assertEquals(Math.sqrt(2), new Vector(new double[]{1, 1})
                .distance(new Vector(new double[]{2, 2})));
    }
}
