package jile.math.abstractalgebra;

import jile.math.annotations.QualityTest;

/**
 * A {@link Monoid} is a unital {@link SemiGroup}.
 * <ul>
 * <li><b>Closure</b>: ∀ a, b ∈ S, a ⋆ b ∈ S</li>
 * <li><b>Associativity</b>: ∀ a, b, c ∈ S, (a ⋆ b) ⋆ c = a ⋆ (b ⋆ c)</li>
 * <li><b>Identity element</b>: ∀ g ∈ S ∃ e that g ⋆ e = e ⋆ g = g ∈ S</li>
 * </ul>
 */

public interface Monoid<T> extends SemiGroup<T> {

    public T getIdentity();

    /**
     * Tests unitalness and invertibility.
     */
    @Override
    default public boolean test() {
        if (!SemiGroup.super.test())
            return false;
        if (!testUnitalness())
            return false;
        return true;
    }

    @QualityTest
    @Override
    default public boolean testUnitalness() {
        return testUnitalness(getSet().choose());
    }

    default public boolean testUnitalness(T x) {
        return operate(x, getIdentity()) == operate(getIdentity(), x);
    }
}
