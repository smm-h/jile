package jile.math.abstractalgebra;

import jile.math.annotations.QualityTest;

/**
 * A {@link UnitalMagma} is a {@link Magma} with an identity-picker nullary
 * operation.
 * <ul>
 * <li><b>Closure</b>: ∀ a, b ∈ S, a ⋆ b ∈ S</li>
 * <li><b>Associativity</b>: ∀ a, b, c ∈ S, (a ⋆ b) ⋆ c = a ⋆ (b ⋆ c)</li>
 * <li><b>Identity element</b>: ∀ g ∈ S ∃ e that g ⋆ e = e ⋆ g = g ∈ S</li>
 * </ul>
 */

public interface UnitalMagma<T> extends Magma<T> {

    public T getIdentity();

    /**
     * Tests unitalness and invertibility.
     */
    @Override
    default public boolean test() {
        if (!Magma.super.test())
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
