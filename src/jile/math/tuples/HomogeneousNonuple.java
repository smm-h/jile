package jile.math.tuples;

/**
 * A {@link HomogeneousNonuple} is a {@link Nonuple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousNonuple<T> extends HomogeneousFiniteTuple<T>, Nonuple<T, T, T, T, T, T, T, T, T> {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 9-tuple");
        }
    }
}