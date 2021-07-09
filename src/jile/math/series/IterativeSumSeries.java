package jile.math.series;

/**
 * An {@link IterativeSumSeries} is a {@link SumSeries} that is calculated
 * iteratively ({@link IterativeSeries}).
 * 
 * @see RecursiveSumSeries
 */
public interface IterativeSumSeries extends IterativeSeries, SumSeries {
    @Override
    default public Double getElementAt(int k) {
        double result = identity;
        for (int i = 0; i < k; i++)
            result += getHostSeries().getElementAt(i);
        return result;
    }
}