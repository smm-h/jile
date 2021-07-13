package jile.math.tuples;

/**
 * An {@link Undecuple} is a {@link FiniteTuple} with a length of 11.
 * <p>
 * <b>Alternative names:</b> hendecuple / hendecad
 */
public interface Undecuple<First, Second, Third, Fourth, Fifth, Sixth, Seventh, Eighth, Ninth, Tenth, Eleventh>
        extends FiniteTuple {
    @Override
    default public Integer getCardinality() {
        return 11;
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
        case 8:
            return getNinth();
        case 9:
            return getTenth();
        case 10:
            return getEleventh();
        default:
            throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 11-tuple");
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

    public Ninth getNinth();

    public Tenth getTenth();

    public Eleventh getEleventh();

}