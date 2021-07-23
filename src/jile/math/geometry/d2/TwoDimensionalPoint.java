package jile.math.geometry.d2;

import jile.common.Cache;
import jile.common.CacheCollisionException;
import jile.common.HashMapCache;
import jile.math.geometry.Point;
import jile.math.numbers.Real;
import jile.math.tuples.Couple;
import jile.math.tuples.HomogeneousCouple;

/**
 * A {@link TwoDimensionalPoint} is a {@link Point} on a {@link Plane} that is
 * defined a {@link Couple} of {@link Real} numbers.
 */
abstract public class TwoDimensionalPoint implements TwoDimensional, Point, HomogeneousCouple<Real> {

    private static final Cache<Point, Real[]> cache = new HashMapCache<Point, Real[]>();

    public static Point fromContents(Real[] contents) {
        assert contents.length == 2;
        Point wrapper;
        wrapper = cache.get(contents);
        if (wrapper != null) {
            return wrapper;
        } else {
            wrapper = new DefaultImplementation(contents[0], contents[1]);
            try {
                cache.add(contents, wrapper);
            } catch (CacheCollisionException e) {
                // TODO should hash collision be silenced?
                e.printStackTrace();
            }
            return wrapper;
        }
    }

    private static class DefaultImplementation extends TwoDimensionalPoint {

        private final Real x, y;

        public DefaultImplementation(Real x, Real y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Real getFirst() {
            return x;
        }

        @Override
        public Real getSecond() {
            return y;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(16); // 1+6+2+6+1
            builder.append("("); // 1
            builder.append(x.toString()); // 6
            builder.append(", "); // 2
            builder.append(y.toString()); // 6
            builder.append(")"); // 1
            return builder.toString();
        }

        @Override
        public int hashCode() {
            return x.hashCode() ^ y.hashCode();
        }
    }
}
