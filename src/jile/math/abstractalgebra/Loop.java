package jile.math.abstractalgebra;

import jile.math.annotations.Convenient;
import jile.math.annotations.QualityTest;
import jile.math.operations.ClosedUnaryOperation;

/**
 * A {@link Loop} is an invertible {@link UnitalMagma}.
 * <ul>
 * <li><b>Closure</b>: ∀ a, b ∈ S, a ⋆ b ∈ S</li>
 * <li><b>Associativity</b>: ∀ a, b, c ∈ S, (a ⋆ b) ⋆ c = a ⋆ (b ⋆ c)</li>
 * <li><b>Identity element</b>: ∀ g ∈ S ∃ e that g ⋆ e = e ⋆ g = g ∈ S</li>
 * <li><b>Inverse element</b>: ∀ g ∈ S ∃ h that g ⋆ h = h ⋆ g = e ∈ S</li>
 * </ul>
 */

public interface Loop<T> extends UnitalMagma<T> {

    public ClosedUnaryOperation<T> getInversion();

    /**
     * Tests unitalness and invertibility.
     */
    @Override
    default public boolean test() {
        if (!UnitalMagma.super.test())
            return false;
        if (!testInvertibility())
            return false;
        return true;
    }

    @Convenient
    default public T inverse(T x) {
        return getInversion().operate(x);
    }

    @QualityTest
    @Override
    default public boolean testInvertibility() {
        return testInvertibility(getSet().choose());
    }

    default public boolean testInvertibility(T x) {
        return operate(x, inverse(x)) == getIdentity() && operate(inverse(x), x) == getIdentity();
    }
}
