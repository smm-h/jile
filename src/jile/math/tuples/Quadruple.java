package jile.math.tuples;

/**
 * A {@link Quadruple} is a {@link FiniteTuple} with a length of 4.
 * <p>
 * <b>Alternative names:</b> quad / tetrad / quartet / quadruplet
 */
public interface Quadruple<First, Second, Third, Fourth> extends FiniteTuple {
    @Override
    default public Integer getCardinality() {
        return 4;
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
        default:
            throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 4-tuple");
        }
    }

    public First getFirst();

    public Second getSecond();

    public Third getThird();

    public Fourth getFourth();
}