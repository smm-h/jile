package jile.math.geometry;

import jile.common.Cache;
import jile.common.CacheCollisionException;
import jile.common.HashMapCache;
import jile.math.numbers.Real;

abstract public class GeneralPoint implements Point {

    private static final Cache<Point, Real[]> cache = new HashMapCache<Point, Real[]>();

    public static Point fromContents(Real[] contents) {
        Point wrapper;
        wrapper = cache.get(contents);
        if (wrapper != null) {
            return wrapper;
        } else {
            wrapper = new DefaultImplementation(contents);
            try {
                cache.add(contents, wrapper);
            } catch (CacheCollisionException e) {
                // TODO should hash collision be silenced?
                e.printStackTrace();
            }
            return wrapper;
        }
    }

    private static class DefaultImplementation extends GeneralPoint {

        private final Real[] array;

        public DefaultImplementation(Real[] array) {
            this.array = array;
        }

        @Override
        public int getDimensions() {
            return array.length;
        }

        @Override
        public Integer getCardinality() {
            return array.length;
        }

        @Override
        public Real getElementAt(int index) {
            return array[index];
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(8 * array.length);
            builder.append("("); // 1
            builder.append(array[0].toString()); // 6
            for (int i = 1; i < array.length; i++) {
                builder.append(", "); // 2
                builder.append(array[i].toString()); // 6
            }
            builder.append(")"); // 1
            return builder.toString();
        }

        @Override
        public int hashCode() {
            int h = 0;
            for (int i = 0; i < array.length; i++)
                h ^= array[i].hashCode();
            return h;
        }
    }

}
