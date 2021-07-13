package jile.math.series;

import jile.math.numbers.Integer;

/**
 * The {@link FactorialSeries} is a {@link ProductSeries} whose host is an
 * {@link OffsetSeries} (and not {@link IdentitySeries}) with an offset of 1,
 * because otherwise the series would be comprised entirely of zeroes.
 * 
 * @see RecursiveFactorialSeries
 * @see IterativeFactorialSeries
 */
abstract public class FactorialSeries implements ProductSeries<Integer> {

    private final Series<Integer> host = new OffsetSeries(1);

    @Override
    public Series<Integer> getHostSeries() {
        return host;
    }
}
