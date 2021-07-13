package jile.math.tuples;

/**
 * A {@link Tredecuple} is a {@link FiniteTuple} with a length of 13.
 * <p>
 * <b>Alternative names:</b> baker's dozen
 * 
 * @see https://en.wikipedia.org/wiki/Dozen#Baker's_dozen
 */
public interface Tredecuple<First, Second, Third, Fourth, Fifth, Sixth, Seventh, Eighth, Ninth, Tenth, Eleventh, Twelveth, Thirteenth>
        extends FiniteTuple {

    @Override
    default public Integer getCardinality() {
        return 13;
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
        case 11:
            return getTwelveth();
        case 12:
            return getThirteenth();
        default:
            throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 13-tuple");
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

    public Twelveth getTwelveth();

    public Thirteenth getThirteenth();

}