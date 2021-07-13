package jile.math.tuples;

/**
 * A {@link HomogeneousDuodecuple} is a {@link Duodecuple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousDuodecuple<T>
        extends HomogeneousFiniteTuple<T>, Duodecuple<T, T, T, T, T, T, T, T, T, T, T, T> {
    @Override
    default public T getElementAt(int index) {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 12-tuple");
        }
    }
}