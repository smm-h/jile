package jile.math.geometry;

import jile.math.settheory.*;

/**
 * A {@link MetricSpace} is a {@link Set} equipped with a {@link Metric} on it.
 * 
 * @see https://en.wikipedia.org/wiki/Metric_space
 */
public interface MetricSpace<M> extends SpecificSet<M> {
    public Metric<M> getMetric();
}
