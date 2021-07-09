package jile.math.series;

/**
 * The {@link FactorialSeries} is a {@link ProductSeries} whose host is an
 * {@link OffsetSeries} (and not {@link IdentitySeries}) with an offset of 1,
 * because otherwise the series would be comprised entirely of zeroes.
 * 
 * @see RecursiveFactorialSeries
 * @see IterativeFactorialSeries
 */
abstract public class FactorialSeries implements ProductSeries {

    private final Series host = new OffsetSeries(1);

    @Override
    public Series getHostSeries() {
        return host;
    }
}
