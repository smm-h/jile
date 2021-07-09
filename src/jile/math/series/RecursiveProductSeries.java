package jile.math.series;

/**
 * The {@link RecursiveProductSeries} is an implementation of the
 * {@link ProductSeries} that is calculated using recursion.
 * 
 * @see IterativeProductSeries
 */
public interface RecursiveProductSeries extends RecursiveSeries, ProductSeries {
    @Override
    default public Double getElementAt(int k) {
        return k == 0 ? identity : getHostSeries().getElementAt(k - 1) * getElementAt(k - 1);
    }
}