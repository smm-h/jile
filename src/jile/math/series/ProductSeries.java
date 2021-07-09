package jile.math.series;

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
public interface ProductSeries extends ParasiteSeries {
    public static final double identity = 1.0;

    @Override
    default int fairLimit() {
        return 20;
    }
}