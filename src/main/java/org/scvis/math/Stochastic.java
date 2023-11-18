package org.scvis.math;

public final class Stochastic {

    private Stochastic() {
        throw new UnsupportedOperationException();
    }

    public static double factorial(short n) {
        double sum = 1;
        while (n > 0) {
            sum *= n;
            n--;
        }
        return sum;
    }

    public static double binom(short n, short k) {
        return factorial(n) / (factorial(k) * factorial((short) (n - k)));
    }

    public static double combU(short n, short k) {
        return binom(n, k);
    }

    public static double combA(short n, short k) {
        return binom((short) (n + k - 1), k);
    }

    public static double bernoulliPdf(double p, short n, short k) {
        return binom(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    public static double bernoulliCdf(double p, short n, short min, short max) {
        double sum = 0;
        while (min <= max) {
            sum += bernoulliPdf(p, n, min);
            min++;
        }
        return sum;
    }

    public static short minimalAttempts(double p, double min, short n) {
        double sum = bernoulliPdf(p, n, (short) 0);
        short k = 0;
        while (sum < min) {
            sum += bernoulliPdf(p, n, ++k);
        }
        return k;
    }
}
