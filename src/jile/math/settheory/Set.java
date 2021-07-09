package jile.math.settheory;

import java.util.List;

import jile.math.numbers.Complex;
import jile.common.*;

/**
 * As the building-block of set theory, a {@link Set} is an unordered collection
 * of some objects.
 * 
 * <h3>Cardinality</h3>
 * <p>
 * The number of elements a set contains is referred to as its "cardinatiy",
 * which can be accessed via {@link #getCardinality}. Cardinality may be
 * infinite.
 * </p>
 * 
 * <h3>Finitivity</h3>
 * <p>
 * A set, if countable, can still be infinite, in which case its
 * {@link #getCardinality} returns null, and its {@link #isFinite} returns
 * false. {@link InfiniteSet} does precisely this.
 * </p>
 * 
 * <h3>Countibility</h3>
 * <p>
 * Considering the limitations inherent in digital computers, all sets are
 * countible, and all infinite sets have the same cardinality: null, denoting
 * aleph-naught. This may change in the future when I find a way to implement
 * uncountably infinite sets such as the continuum. (TODO)
 * </p>
 * 
 * <h3>Elements</h3>
 * <p>
 * Although the elements of a set are often numbers such as {@link Integer}s or
 * {@link Complex}, they may also be non-numerical objects such as polynomials,
 * square matrices, functions, and power series. Strictly speaking a set can
 * Java object, even itself. Do not call {@link #toTree} if a set contains
 * itself as it will enter a "forever" loop.
 * </p>
 * 
 * <h3>Nullibility</h3>
 * <p>
 * A set may also contain the special object "null", in which case it is said
 * that the set is "nullible".
 * </p>
 */
public interface Set {

    /**
     * Returns whether or not an object is an element of this set.
     */
    public boolean containsUnchecked(Object object);

    /**
     * Returns whether or not this set is nullible, meaning it contains the special
     * and implementation-specific unique object, "null".
     */
    public boolean containsNull();

    /**
     * Chooses and returns a random element from this set. Useful when testing
     * various properties of the set in different contexts.
     * <p>
     * This property is axiomatically assumed to be trivially feasable for all sets.
     * 
     * @see https://en.wikipedia.org/wiki/Choice_function
     * @see https://en.wikipedia.org/wiki/Axiom_of_choice
     */
    public Object choose();

    /**
     * Returns n arrays of random element from this set. Useful when debugging.
     * 
     * @implNote this uses {@link #choose} internally.
     */
    default public Object[] chooseMany(int n) {
        Object[] array = new Object[n];
        for (int i = 0; i < n; i++)
            array[i] = choose();
        return array;
    }

    /**
     * Returns the cardinality, or the number of elements of the set.
     */
    public Integer getCardinality();

    /**
     * Returns whether or not this set is finite, informally meaning "one could
     * start counting its elements and finish counting".
     * 
     * @apiNote Rather than checking {@code instanceof} {@link FiniteSet}, always
     *          use this method.
     */
    public boolean isFinite();

    /**
     * Returns the opposite of {@link #isFinite}, unless overridden.
     * 
     * @apiNote Rather than checking {@code instanceof} {@link InfiniteSet}, always
     *          use this method.
     */
    default public boolean isInfinite() {
        return !isFinite();
    }

    /**
     * Returns whether or not this set is finite, informally meaning "one could
     * start counting its elements and finish counting".
     * 
     * @apiNote Rather than checking {@code instanceof} {@link NullibleSet}, always
     *          use this method.
     */
    default public boolean isNullible() {
        return containsNull();
    }

    /**
     * Returns the opposite of {@link #isNullible}, unless overridden.
     * 
     * @apiNote Rather than checking {@code instanceof} {@link NonNullibleSet},
     *          always use this method.
     */
    default public boolean isNonNullible() {
        return !isNullible();
    }

    /**
     * Returns whether or not a set is countable.
     * <p>
     * <i>Currently all sets are assumed to be countable. This may change in the
     * future.</i>
     * <p>
     * A set is countable that has less or equal cardinality as the set of natural
     * numbers. A countable set is either finite or countably infinite set. Either
     * way, the elements of a countable set can always be counted one at a time and
     * although the counting may never finish, every element of the set is
     * associated with a unique natural number.
     */
    default public boolean isCountable() {
        return true;
    }

    /**
     * Returns the opposite of {@link #isCountable}, unless overridden.
     */
    default public boolean isNoncountable() {
        return !isCountable();
    }

    // /**
    // * Returns the union operation.
    // */
    // public ClosedBinaryOperation<Set> getUnion(Set other);

    // /**
    // * Returns the intersection operation.
    // */
    // public ClosedBinaryOperation<Set> getIntersection(Set other);

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
    // public static <T, S extends Set> UnionSet union(S... sets) {
    // return new GenericUnionSet<T, S>(sets);
    // }

    default public Tree<Object> toTree() {
        LinkedTree<Object> tree = new LinkedTree<Object>();
        toTree(tree);
        return tree;
    }

    // @SuppressWarnings("unchecked")
    default public void toTree(MutableTree<Object> tree) {
        tree.addAndGoTo(this);
        for (Object object : excerpt()) {
            Object element = (Object) object;
            if (element instanceof Set) {
                ((Set) element).toTree(tree);
            } else {
                tree.add(element);
            }
        }
        tree.goBack();
    }

    default public List<?> excerpt() {
        throw new UnsupportedOperationException("Set excerpt undefined");
    }
}
