package jile.math.series;

import jile.math.numbers.Real;

/**
 * A {@link ProductSeries} is a {@link ParasiteSeries} whose each element is
 * calculated as the product of all the elements of the host {@link Series}
 * before and up to it.
 * 
 * @apiNote The host series of a product series must not produce zero otherwise
 *          the entire parasite series will be comprised of zeroes. This is why
 *          {@link FactorialSeries} uses an {@link OffsetSeries} instead of the
 *          {@link IdentitySeries}.
 * 
 * @see SumSeries
 */
public interface ProductSeries<H extends Real> extends ParasiteSeries<Real, H> {
    public static final Real identity = Real.ONE;

    @Override
    default int fairLimit() {
        return 20;
    }
}