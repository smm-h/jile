package jile.math.series;

/**
 * An {@link OffsetSeries} is a {@link Series} that maps each ordinal to itself
 * added by a constant integer called the "offset".
 * 
 * @see IdentitySeries
 */
public class OffsetSeries implements Series {

    private final int offset;

    /**
     * @param offset cannot be zero; use {@link IdentitySeries} instead.
     */
    public OffsetSeries(int offset) {
        if (offset == 0)
            throw new IllegalArgumentException("offset cannot be zero");
        this.offset = offset;
    }

    @Override
    public int fairLimit() {
        return 1000;
    }

    @Override
    public boolean contains(Double k) {
        if (k != null) {
            if (Math.floor(k) == k) {
                if (k >= offset) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Double getElementAt(int k) {
        return (double) (k + offset);
    }

}
