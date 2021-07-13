package jile.math.settheory;

import java.util.List;

/**
 * A {@link SpecificSet} is a {@link Set} whose elements are all members of a
 * certain Java type T.
 */
public interface SpecificSet<T> extends Set {

    /**
     * Returns whether or not an object of type T is an element of this set.
     */
    public boolean contains(T object);

    @Override
    @SuppressWarnings("unchecked")
    default public boolean containsUnchecked(Object object) throws ClassCastException {
        return contains((T) object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T choose();

    // /**
    // * {@inheritDoc}
    // */
    // @Override
    // @SuppressWarnings("unchecked")
    // default public T[] chooseMany(int n) {
    // T[] array = (T[]) new Object[n];
    // for (int i = 0; i < n; i++)
    // array[i] = choose();
    // return array;
    // }

    // /**
    // * Returns the union operation.
    // */
    // public ClosedBinaryOperation<Set<T>> getUnion(Set<T> other);

    // /**
    // * Returns the intersection operation.
    // */
    // public ClosedBinaryOperation<Set<T>> getIntersection(Set<T> other);

    // /**
    // * Returns another set that represents the union of the given sets, that can
    // be
    // * of different types.
    // */
    // public static UnionSet<Object> nonGenericUnion(Set<?>... sets) {
    // return new NonGenericUnionSet(sets);
    // }
    //
    // /**
    // * Returns another set that represents the union of the given sets, that are
    // all
    // * of the same type.
    // */
    // @SuppressWarnings("unchecked")
    // public static <T, S extends Set<T>> UnionSet<T> union(S... sets) {
    // return new GenericUnionSet<T, S>(sets);
    // }

    @Override
    default public List<T> excerpt() {
        throw new UnsupportedOperationException("Set excerpt undefined");
    }
}
