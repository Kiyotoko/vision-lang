package org.scvis.math;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPolygon {

    @Test
    void contains() {
        Polygon polygon = new Polygon(List.of(new Point2D(0, 0), new Point2D(10, 0),
                new Point2D(10, 10), new Point2D(0, 10)));
        assertTrue(polygon.contains(new Point2D(8, 8)));
        assertFalse(polygon.contains(new Point2D(11, 0)));
    }
}
