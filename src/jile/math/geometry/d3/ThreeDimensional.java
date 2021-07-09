package jile.math.geometry.d3;

import jile.math.geometry.Dimensional;
import jile.math.geometry.d2.TwoDimensional;

/**
 * Anything {@link ThreeDimensional} is 3-{@link Dimensional}. This type is
 * meant to be the super-type for almost every other type in this package.
 * 
 * @see TwoDimensional
 */
public interface ThreeDimensional extends Dimensional {
    @Override
    default int getDimensions() {
        return 3;
    }
}
