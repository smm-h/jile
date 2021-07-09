package jile.math.series;

public interface RecursiveHigherOrderFibonacciSeries extends HigherOrderFibonacciSeries {

    @Override
    default public int fairLimit() {
        return 60 / getOrder();
    }

    @Override
    default public Double getElementAt(int index) {
        int n = getOrder();
        if (index < n) {
            return index == (n - 1) ? 1.0 : 0.0;
        } else {
            double s = 0.0;
            for (int i = 1; i <= n; i++)
                s += getElementAt(index - i);
            return s;
        }
    }
}
