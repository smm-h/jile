package jile.math.tuples;

/**
 * A {@link Triple} is a {@link FiniteTuple} with a length of 3.
 * <p>
 * <b>Alternative names:</b> treble / triplet / triad / ordered triple /
 * threesome
 */
public interface Triple<First, Second, Third> extends FiniteTuple {
    @Override
    default public Integer getCardinality() {
        return 3;
    }

    @Override
    default public Object getElementAt(int index) {
        switch (index) {
        case 0:
            return getFirst();
        case 1:
            return getSecond();
        case 2:
            return getThird();
        default:
            throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 3-tuple");
        }
    }

    public First getFirst();

    public Second getSecond();

    public Third getThird();

}