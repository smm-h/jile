package jile.math.tuples;

/**
 * A {@link HomogeneousTriple} is a {@link Triple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousTriple<T> extends HomogeneousFiniteTuple<T>, Triple<T, T, T> {
    @Override
    default public T getElementAt(int index) {
        switch (index) {
            case 0:
                return getFirst();
            case 1:
                return getSecond();
            case 2:
                return getThird();
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 3-tuple");
        }
    }
}