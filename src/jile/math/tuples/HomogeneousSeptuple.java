package jile.math.tuples;

/**
 * A {@link HomogeneousSeptuple} is a {@link Septuple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousSeptuple<T> extends HomogeneousFiniteTuple<T>, Septuple<T, T, T, T, T, T, T> {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 7-tuple");
        }
    }
}