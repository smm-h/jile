package jile.math.series;

import jile.common.Singleton;

/**
 * The {@link IdentitySeries} is a {@link Series} that maps each ordinal to
 * itself. In other words, it is the infinite sequence in which each element is
 * equal to its index.
 * 
 * @see OffsetSeries
 */
public class IdentitySeries implements Series, Singleton {

    private IdentitySeries() {
    }

    private static IdentitySeries singleton;

    public static IdentitySeries singleton() {
        if (singleton == null) {
            singleton = new IdentitySeries();
        }
        return singleton;
    }

    @Override
    public int fairLimit() {
        return 1000;
    }

    @Override
    public boolean contains(Double k) {
        return k != null && Math.floor(k) == k;
    }

    @Override
    public Double getElementAt(int k) {
        return (double) k;
    }

}
