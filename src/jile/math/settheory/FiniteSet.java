package jile.math.settheory;

/**
 * A {@link FiniteSet} is a {@link Set} whose cardinality is less than
 * aleph-naught.
 */
public interface FiniteSet extends Set {
    default public boolean isFinite() {
        return true;
    }
}
