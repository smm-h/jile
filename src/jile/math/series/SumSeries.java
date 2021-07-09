package jile.math.series;

/**
 * A {@link SumSeries} is a {@link ParasiteSeries} whose each element is
 * calculated as the sum of all the elements of the host {@link Series} before
 * and up to it.
 * 
 * @see ProductSeries
 */
public interface SumSeries extends ParasiteSeries {
    public static final double identity = 0.0;

    @Override
    default int fairLimit() {
        return 100;
    }
}
