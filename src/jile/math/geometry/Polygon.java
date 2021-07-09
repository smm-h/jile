package jile.math.geometry;

import jile.math.numbers.Real;

/**
 * A {@link Polygon} is a closed {@link PolygonalChain}.
 */
public interface Polygon extends PolygonalChain {

    public Real getPerimeter();
}
