package jile.math.series;

import jile.math.numbers.Integer;

/**
 * An {@link OffsetSeries} is a {@link Series} that maps each ordinal to itself
 * added by a constant integer called the "offset".
 * 
 * @see IdentitySeries
 */
public class OffsetSeries implements Series<Integer> {

    private final Integer offset;

    /**
     * @param offset cannot be zero; use {@link IdentitySeries} instead.
     */
    public OffsetSeries(int offset) {
        if (offset == 0)
            throw new IllegalArgumentException("offset cannot be zero");
        this.offset = Integer.fromContents(offset);
    }

    @Override
    public int fairLimit() {
        return 1000;
    }

    @Override
    public boolean contains(Integer k) {
        if (k != null) {
            if (k.compareTo(offset) >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getElementAt(int k) {
        return offset.add(k);
    }

}
