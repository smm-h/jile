package jile.math.series;

/**
 * A {@link ParasiteSeries} is a {@link Series} that internally uses the
 * elements of another series, called the "host" series, to produce its own
 * elements.
 * 
 * @see SumSeries
 * @see ProductSeries
 * @see FactorialSeries
 */
public interface ParasiteSeries extends Series {
    public Series getHostSeries();
}
