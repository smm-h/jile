package jile.math.tuples;

/**
 * A {@link Sextuple} is a {@link FiniteTuple} with a length of 6.
 * <p>
 * <b>Alternative names:</b> hextuple / hexad
 */
public interface Sextuple<First, Second, Third, Fourth, Fifth, Sixth> extends FiniteTuple {

    @Override
    default public Integer getCardinality() {
        return 6;
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
        case 3:
            return getFourth();
        case 4:
            return getFifth();
        case 5:
            return getSixth();
        default:
            throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 6-tuple");
        }
    }

    public First getFirst();

    public Second getSecond();

    public Third getThird();

    public Fourth getFourth();

    public Fifth getFifth();

    public Sixth getSixth();

}