package jile.math.tuples;

/**
 * A {@link HomogeneousDecuple} is a {@link Decuple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousDecuple<T> extends HomogeneousFiniteTuple<T>, Decuple<T, T, T, T, T, T, T, T, T, T> {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 10-tuple");
        }
    }
}