package jile.math.geometry.d2;

import jile.math.geometry.Dimensional;
import jile.math.geometry.d3.ThreeDimensional;

/**
 * Anything {@link TwoDimensional} is 2-{@link Dimensional}. This type is meant
 * to be the super-type for almost every other type in this package.
 * 
 * @see ThreeDimensional
 */
public interface TwoDimensional extends Dimensional {
    @Override
    default public int getDimensions() {
        return 2;
    }
}
