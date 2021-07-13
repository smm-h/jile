package jile.math.settheory;

/**
 * Most {@link UniversalSet}s studied are infinite; for example {@link Set#R}
 * and {@link Set#I} contain an infinite number of elements.
 * {@link InfiniteUniversalSet} is the base interface for those sets.
 * 
 * @see FiniteUniversalSet
 */
public interface InfiniteUniversalSet<T> extends UniversalSet<T>, InfiniteSet {
}
