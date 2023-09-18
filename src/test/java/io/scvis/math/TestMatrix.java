package io.scvis.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestMatrix {

    @Test
    void add() {
        Matrix a = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix b = new Matrix(new double[][]{{5, 6}, {7, 8}});

        Assertions.assertDoesNotThrow(( ) -> {
            System.out.println(a.add(b));
        });
    }

    @Test
    void subtract() {
        Matrix a = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix b = new Matrix(new double[][]{{5, 6}, {7, 8}});

        Assertions.assertDoesNotThrow(( ) -> {
            System.out.println(a.subtract(b));
        });
    }

    @Test
    void multiply() {
        Matrix a = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix b = new Matrix(new double[][]{{5, 6}, {7, 8}});

        Assertions.assertDoesNotThrow(() -> {
            System.out.println(a.multiply(b));
        });
    }
}
