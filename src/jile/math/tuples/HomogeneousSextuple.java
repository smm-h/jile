package jile.math.tuples;

/**
 * A {@link HomogeneousSextuple} is a {@link Sextuple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousSextuple<T> extends HomogeneousFiniteTuple<T>, Sextuple<T, T, T, T, T, T> {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 6-tuple");
        }
    }
}