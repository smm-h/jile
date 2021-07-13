package jile.math.series;

import jile.math.annotations.BadDesign;

/**
 * An {@link IterativeSeries} is a {@link Series}, the calculation of whose
 * elements involve an iteration of typically O(n) where n is the index of the
 * element being calculated. These series are often also {@link ParasiteSeries}.
 * 
 * @see RecursiveSeries
 */
@BadDesign
public interface IterativeSeries {
}
