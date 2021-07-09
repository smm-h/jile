package jile.math.geometry;

/**
 * A {@link Plane} is a 2-dimensional {@link EuclideanSpace}.
 */
public interface Plane extends EuclideanSpace {
    @Override
    default int getDimensions() {
        return 2;
    }
}
