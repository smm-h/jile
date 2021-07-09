package jile.math.abstractalgebra;

import jile.math.operations.*;
import jile.math.settheory.Set;

/**
 * A {@link NearRing} is an {@link AlgebraicStructure} that is a {@link Group}
 * under {@link Addition}, a {@link SemiGroup} under {@link Multiplication}, and
 * whose multiplication distributes on the right over addition.
 * 
 * @see Ring
 */

public interface NearRing<T> extends AlgebraicStructure {

    @Override
    default public Set getSet() {
        return getGroup().getSet();
    }

    @Override
    default public Operation[] getOperations() {
        Operation[] operations = { getAddition(), getMultiplication() };
        return operations;
    }

    public AdditiveGroup<T> getGroup();

    default public Addition<T> getAddition() {
        return getGroup().getOperation();
    }

    public ClosedBinaryOperation<T> getMultiplication();

    default public AdditiveInversion<T> getAdditiveInvertion() {
        return getGroup().getInversion();
    }

    default public Subtraction<T> makeSubtraction() {
        return new Subtraction<T>() {

            private Addition<T> addition = getAddition();
            private AdditiveInversion<T> additiveInversion = getAdditiveInvertion();

            @Override
            public T operate(T a, T b) {
                return addition.operate(a, additiveInversion.operate(b));
            }
        };
    }

    @Override
    default public boolean test() {
        if (!getGroup().test())
            return false;
        if (!getMultiplication().isAssociative(getGroup().getSet()))
            return false;
        if (!getMultiplication().isRightDistributive(getAddition(), getGroup().getSet()))
            return false;
        return true;
    }
}
