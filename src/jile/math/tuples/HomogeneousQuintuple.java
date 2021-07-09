package jile.math.tuples;

/**
 * A {@link HomogeneousQuintuple} is a {@link Quintuple} that is homogeneous.
 *
 * @see HomogeneousTuple
 */
public interface HomogeneousQuintuple<T> extends HomogeneousFiniteTuple<T>, Quintuple<T, T, T, T, T> {
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
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 5-tuple");
        }
    }
}