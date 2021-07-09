package jile.math.series;

/**
 * The {@link IterativeProductSeries} is an implementation of the
 * {@link ProductSeries} that is calculated without the use of recursion.
 * 
 * @see RecursiveProductSeries
 */
public interface IterativeProductSeries extends IterativeSeries, ProductSeries {
    @Override
    default public Double getElementAt(int k) {
        double result = identity;
        for (int i = 0; i < k; i++)
            result *= getHostSeries().getElementAt(i);
        return result;
    }
}