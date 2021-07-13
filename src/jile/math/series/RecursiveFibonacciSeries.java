package jile.math.series;

/**
 * The {@link RecursiveFibonacciSeries} is an implementation of the
 * {@link FibonacciSeries} which uses recursion.
 */
public class RecursiveFibonacciSeries implements RecursiveHigherOrderFibonacciSeries, FibonacciSeries {

    public static void main(String[] args) {
        System.out.println(new RecursiveFibonacciSeries().excerpt());
    }
}
