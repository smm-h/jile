package jile.math.tuples;

import java.util.Objects;

/**
 * A {@link Monuple} is a {@link FiniteTuple} with a length of 1.
 * <p>
 * <b>Alternative names:</b> single / singleton / monad
 */
public interface Monuple<First> extends FiniteTuple {
    @Override
    default public Integer getCardinality() {
        return 1;
    }

    @Override
    default public Object getElementAt(int index) {
        switch (index) {
            case 0:
                return getFirst();
            default:
                throw new IndexOutOfBoundsException("trying to access index: " + index + " in a 1-tuple");
        }
    }

    @Override
    default boolean containsUnchecked(Object object) {
        return Objects.equals(getFirst(), object);
    }

    public First getFirst();

}