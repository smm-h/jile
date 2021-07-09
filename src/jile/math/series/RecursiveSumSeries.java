package jile.math.series;

/**
 * A {@link RecursiveSumSeries} is a {@link SumSeries} that is calculated
 * recursively ({@link RecursiveSeries}).
 * 
 * @see IterativeSumSeries
 */
public interface RecursiveSumSeries extends RecursiveSeries, SumSeries {
    @Override
    default public Double getElementAt(int k) {
        return k == 0 ? identity : getHostSeries().getElementAt(k - 1) + getElementAt(k - 1);
    }
}