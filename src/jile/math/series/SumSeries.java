package jile.math.series;

import jile.math.numbers.Real;

/**
 * A {@link SumSeries} is a {@link ParasiteSeries} whose each element is
 * calculated as the sum of all the elements of the host {@link Series} before
 * and up to it.
 * 
 * @see ProductSeries
 */
public interface SumSeries<H extends Real> extends ParasiteSeries<Real, H> {
    public static final Real identity = Real.ZERO;

    @Override
    default int fairLimit() {
        return 100;
    }
}
