package jile.math.abstractalgebra;

import jile.math.annotations.Convenient;
import jile.math.annotations.QualityTest;
import jile.math.operations.ClosedBinaryOperation;
import jile.math.operations.ClosedOperation;
import jile.math.settheory.SpecificSet;

/**
 * A {@link Magma} is an {@link AlgebraicStructure} (S, ⋆) where S is a
 * non-empty {@link SpecificSet} and ⋆ is a {@link ClosedBinaryOperation} on it.
 * <ul>
 * <li><b>Closure</b>: ∀ a, b ∈ S, a ⋆ b ∈ S</li>
 * </ul>
 * 
 * @see SemiGroup
 * @see Group
 */

public interface Magma<T> extends AlgebraicStructure {

    @Override
    public SpecificSet<T> getSet();

    public ClosedBinaryOperation<T> getOperation();

    @SuppressWarnings("unchecked")
    @Override
    default public ClosedOperation<T>[] getOperations() {
        ClosedOperation<?>[] operations = { getOperation() };
        return (ClosedOperation<T>[]) operations;
    }

    /**
     * Tests the non-null-ness of the set and the operation.
     */
    @Override
    default public boolean test() {
        if (getSet() == null)
            return false;
        if (getOperation() == null)
            return false;
        return true;
    }

    @Convenient
    default public T operate(T x, T y) {
        return getOperation().operate(x, y);
    }

    /**
     * Overridden in {@link SemiGroup} and {@link Group}
     */
    @QualityTest
    default public boolean testAssociativity() {
        return false;
    }

    /**
     * Overridden in {@link Monoid}
     */
    @QualityTest
    default public boolean testUnitalness() {
        return false;
    }

    /**
     * Overridden in {@link Group}
     */
    @QualityTest
    default public boolean testInvertibility() {
        return false;
    }
}
