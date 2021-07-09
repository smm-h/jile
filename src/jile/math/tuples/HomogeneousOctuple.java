package jile.math.tuples;

/**
 * A {@link HomogeneousOctuple} is a {@link Octuple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousOctuple<T> extends HomogeneousFiniteTuple<T>, Octuple<T, T, T, T, T, T, T, T> {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 8-tuple");
        }
    }
}