package jile.math.series;

import jile.common.Singleton;
import jile.math.numbers.Integer;

/**
 * The {@link IdentitySeries} is a {@link Series} that maps each ordinal to
 * itself. In other words, it is the infinite sequence in which each element is
 * equal to its index.
 * 
 * @see OffsetSeries
 */
public class IdentitySeries implements Series<Integer>, Singleton {

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
    public boolean contains(Integer k) {
        return k != null;
    }

    @Override
    public Integer getElementAt(int k) {
        return Integer.fromContents(k);
    }

}
