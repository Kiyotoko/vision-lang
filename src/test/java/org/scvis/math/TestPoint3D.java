package org.scvis.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestPoint3D {

    @Test
    void isMultiplicative() {
        assertTrue(new Point3D(2, 4, 6).isMultiplicative(new Point3D(4, 8, 12)));
        assertFalse(new Point3D(2, 4, 6).isMultiplicative(new Point3D(4, 8, 11)));
        assertTrue(new Point3D(0, 0, 0).isMultiplicative(new Point3D(0, 0, 0)));
        assertFalse(new Point3D(0, 0, 0).isMultiplicative(new Point3D(1, 0, 0)));
    }

    @Test
    void getMultiplicative() {
        assertEquals(2, new Point3D(2, 4, 6).getMultiplicative(new Point3D(4, 8, 12)));
        assertEquals(Double.NaN, new Point3D(2, 4, 6).getMultiplicative(new Point3D(4, 8, 11)));
        assertEquals(0, new Point3D(0, 0, 0).getMultiplicative(new Point3D(0, 0, 0)));
        assertEquals(Double.NaN, new Point3D(0, 0, 0).getMultiplicative(new Point3D(1, 0, 0)));
    }

    @Test
    void midpoint() {
        assertEquals(new Point3D(2, 2, 2), new Point3D(1, 1, 1).midpoint(new Point3D(3, 3, 3)));
    }

    @Test
    void normalize() {
        assertEquals(new Point3D(1, 0, 0), new Point3D(1, 0, 0).normalize());
        assertEquals(new Point3D(1 / Math.sqrt(2), 1 / Math.sqrt(2), 0), new Point3D(1, 1, 0).normalize());
        assertEquals(Point3D.ZERO, new Point3D(0, 0, 0).normalize());
    }

    @Test
    void magnitude() {
        assertEquals(Math.sqrt(3), new Point3D(1, 1, 1).magnitude());
    }
}
