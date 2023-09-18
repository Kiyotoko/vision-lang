package io.scvis.math;

public final class Stochastic {

    private Stochastic() {
        throw new UnsupportedOperationException();
    }

    public static long factorial(int n) {
        long sum = 1;
        while (n > 0) {
            sum *= n;
            n--;
        }
        return sum;
    }

    public static long binom(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static long combU(int n, int k) {
        return binom(n, k);
    }

    public static long combA(int n, int k){
        return binom(n + k - 1, k);
    }

    public static double bernoulliPdf(double p, int n, int k) {
        return binom(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    public static double bernoulliCdf(double p, int n, int min, int max) {
        double sum = 0;
        while (min <= max) {
            sum += bernoulliPdf(p, n, min);
            min ++;
        }
        return sum;
    }

    public static int minimalAttempts(double p, double min, int n) {
        double sum = bernoulliPdf(p, n, 0);
        int k = 0;
        while (sum < min) {
            sum += bernoulliPdf(p, n, ++k);
        }
        return k;
    }
}
