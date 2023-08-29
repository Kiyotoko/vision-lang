package io.scvis.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TestStochastic {

    @ParameterizedTest
    @CsvSource("0.25, 0.5, 2, 2")
    void bernoulliPdf(double exp, double p, int n, int k) {
        assertEquals(exp, Stochastic.bernoulliPdf(p, n, k));
    }


    @ParameterizedTest
    @CsvSource("1.0, 0.5, 2, 0, 2")
    void bernoulliCdf(double exp, double p, int n, int min, int max) {
        assertEquals(exp, Stochastic.bernoulliCdf(p, n, min, max));
    }
}
