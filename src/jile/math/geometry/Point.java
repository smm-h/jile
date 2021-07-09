package jile.math.geometry;

import jile.math.geometry.d2.TwoDimensionalPoint;
import jile.math.geometry.d3.ThreeDimensionalPoint;
import jile.math.numbers.Real;
import jile.math.tuples.*;
import jile.common.HashMapCache;
import jile.common.Cache;
import jile.common.CacheCollisionException;
import jile.common.Cacheable;

/**
 * A {@link Point} is an n-dimensional {@link GeometricalObject}, defined as an
 * n-{@link Tuple} of {@link Real} numbers, that denotes an exact location in an
 * n-dimensional {@link EuclideanSpace}.
 * 
 * @see HomogeneousTuple
 * @see TwoDimensionalPoint
 * @see ThreeDimensionalPoint
 */
public interface Point extends Dimensional, HomogeneousFiniteTuple<Real>, Cacheable<Real[]> {

    static final Cache<Point, Real[]> cache = new HashMapCache<Point, Real[]>();

    public static Point fromContents(Real[] contents) {
        Point wrapper;
        wrapper = cache.get(contents);
        if (wrapper != null) {
            return wrapper;
        } else {
            switch (contents.length) {
                case 2:
                    wrapper = new TwoDimensionalPoint(contents[0], contents[1]);
                    break;
                case 3:
                    wrapper = new ThreeDimensionalPoint(contents[0], contents[1], contents[2]);
                    break;
                default:
                    wrapper = new GeneralPoint(contents.clone());
                    break;
            }
            try {
                cache.add(contents, wrapper);
            } catch (CacheCollisionException e) {
                // TODO should hash collision be silenced?
                e.printStackTrace();
            }
            return wrapper;
        }
    }

    @Override
    default Real[] getCached() {
        int n = getDimensions();
        Real[] worth = new Real[n];
        for (int i = 0; i < n; i++)
            worth[i] = getElementAt(i);
        return worth;
    }

    public static boolean equals(Point self, Object other) {
        if (self == null || other == null) {
            return false;
        } else if (other instanceof Point) {
            return equals(self, (Point) other);
        } else if (other instanceof java.awt.geom.Point2D) {
            return equals(self, (java.awt.geom.Point2D) other);
        } else {
            return false;
        }
    }

    public static boolean equals(Point self, Point other) {
        int n = self.getDimensions();
        if (n == other.getDimensions()) {
            for (int i = 0; i < n; i++) {
                if (!self.getElementAt(i).equals(other.getElementAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This checks a {@link Point} against an object of type
     * {@link java.awt.geom.Point2D} which is the ancestor of {@link java.awt.Point}
     * and {@link jile.vis.Point}.
     */
    @SuppressWarnings("unrelated")
    public static boolean equals(Point self, java.awt.geom.Point2D other) {
        if (self.getDimensions() == 2) {
            return self.getElementAt(0).equals((Object) other.getX())
                    && self.getElementAt(1).equals((Object) other.getY());
        } else {
            return false;
        }
    }
}