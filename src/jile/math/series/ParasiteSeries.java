package jile.math.series;

import jile.math.numbers.Real;

/**
 * A {@link ParasiteSeries} is a {@link Series} that internally uses the
 * elements of another series, called the "host" series, to produce its own
 * elements.
 * 
 * @see SumSeries
 * @see ProductSeries
 * @see FactorialSeries
 */
public interface ParasiteSeries<T extends Real, H extends T> extends Series<T> {
    public Series<H> getHostSeries();
}
