package jile.math.series;

public interface FibonacciSeries extends HigherOrderFibonacciSeries {
    @Override
    default public int getOrder() {
        return 2;
    }
}
