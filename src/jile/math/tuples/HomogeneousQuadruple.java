package jile.math.tuples;

/**
 * A {@link HomogeneousQuadruple} is a {@link Quadruple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousQuadruple<T> extends HomogeneousFiniteTuple<T>, Quadruple<T, T, T, T> {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 4-tuple");
        }
    }
}