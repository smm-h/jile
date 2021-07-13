package jile.math.abstractalgebra;

import jile.math.annotations.QualityTest;

/**
 * A {@link SemiGroup} is a {@link Magma} in which the operation is associative.
 * <ul>
 * <li><b>Closure</b>: ∀ a, b ∈ S, a ⋆ b ∈ S</li>
 * <li><b>Associativity</b>: ∀ a, b, c ∈ S, (a ⋆ b) ⋆ c = a ⋆ (b ⋆ c)</li>
 * </ul>
 * 
 * @see Group
 */

public interface SemiGroup<T> extends Magma<T> {

    /**
     * Tests the associativity of the operation over the set.
     */
    @Override
    default public boolean test() {
        if (!Magma.super.test())
            return false;
        if (!testAssociativity())
            return false;
        return true;
    }

    @QualityTest
    default public boolean testAssociativity() {
        // @formatter:off
        return getOperation().isAssociative(
            getSet().choose(),
            getSet().choose(),
            getSet().choose()
        );
    }
}
