package jile.math.tuples;

/**
 * An {@link Octuple} is a {@link FiniteTuple} with a length of 8.
 * <p>
 * <b>Alternative names:</b> octa / octet / octad / octuplet
 */
public interface Octuple<First, Second, Third, Fourth, Fifth, Sixth, Seventh, Eighth> extends FiniteTuple {
    @Override
    default public Integer getCardinality() {
        return 8;
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
        case 6:
            return getSeventh();
        case 7:
            return getEighth();
        default:
            throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 8-tuple");
        }
    }

    public First getFirst();

    public Second getSecond();

    public Third getThird();

    public Fourth getFourth();

    public Fifth getFifth();

    public Sixth getSixth();

    public Seventh getSeventh();

    public Eighth getEighth();

}