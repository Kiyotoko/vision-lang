package org.scvis.math;

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

    @ParameterizedTest
    @CsvSource({"6, 3", "40_320, 8", "1_307_674_368_000, 15"})
    void factorial(long exp, int val) {
        assertEquals(exp, Stochastic.factorial(val));
    }


    @ParameterizedTest
    @CsvSource({"3, 3, 2", "4, 4, 3", "3, 3, 1", "6, 4, 2"})
    void combU(int exp, int n, int k) {
        assertEquals(exp, Stochastic.combU(n, k));
    }

    @ParameterizedTest
    @CsvSource({"6, 3, 2", "20, 4, 3", "3, 3, 1", "10, 4, 2"})
    void combA(int exp, int n, int k) {
        assertEquals(exp, Stochastic.combA(n, k));
    }

    @ParameterizedTest
    @CsvSource({"1, 0.5, 0.5, 2", "2, 0.5, 1.0, 2", "3, 0.2, 0.8, 10"})
    void minimalAttempts(int exp, double p, double min, int n) {
        int result = Stochastic.minimalAttempts(p, min, n);
        assertEquals(exp, result);
        assertTrue(Stochastic.bernoulliCdf(p, n, 0, result) >= min);
        assertFalse(Stochastic.bernoulliCdf(p, n, 0, result - 1) >= min);
    }
}
