package jile.math.tuples;

/**
 * A {@link HomogeneousCouple} is a {@link Couple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousCouple<T> extends HomogeneousFiniteTuple<T>, Couple<T, T> {
    @Override
    default public T getElementAt(int index) {
        switch (index) {
            case 0:
                return getFirst();
            case 1:
                return getSecond();
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 2-tuple");
        }
    }
}