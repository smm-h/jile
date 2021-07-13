package jile.math.geometry;

import jile.math.exceptions.DimensionMismatchException;
import jile.math.numbers.Real;

/**
 * The {@link EuclideanDistance} between two {@link Point}s in a
 * {@link EuclideanSpace} is the length of the {@link LineSegment} between them.
 * 
 * @see https://en.wikipedia.org/wiki/Euclidean_distance
 * @see Metric
 */
public class EuclideanDistance implements Metric<Point> {
    private EuclideanDistance() {
    }

    private static EuclideanDistance singleton;

    public static EuclideanDistance singleton() {
        if (singleton == null) {
            singleton = new EuclideanDistance();
        }
        return singleton;
    }

    @Override
    public Real distance(Point a, Point b) {
        int d = a.getDimensions();
        if (d != b.getDimensions())
            throw new DimensionMismatchException();
        Real L = Real.ZERO;
        for (int i = 0; i < d; i++)
            L = L.add((a.getElementAt(i).subtract(b.getElementAt(i)).sqr()));
        return L.sqrt();
    }
}
