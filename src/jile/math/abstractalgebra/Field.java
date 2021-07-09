package jile.math.abstractalgebra;

import jile.math.operations.ClosedOperation;
import jile.math.settheory.SpecificSet;

/**
 * A {@link Field} embodies not only a {@link UnitaryRing}, but also the
 * {@link AbelianGroup} for that {@link Ring} and the {@link SpecificSet} for
 * that {@link Group}; this means the functionalities of a set, a group, and a
 * ring are all exposed by a field.
 * 
 * @see https://math.stackexchange.com/questions/164114/is-there-any-difference-between-the-definition-of-a-commutative-ring-and-field#comment377820_164114
 */
public interface Field<T> extends SpecificSet<T>, AdditiveAbelianGroup<T>, UnitaryRing<T> {

    @Override
    default public SpecificSet<T> getSet() {
        return this;
    }

    @Override
    default public ClosedOperation<T>[] getOperations() {
        return AdditiveAbelianGroup.super.getOperations();
    }

    @Override
    default public boolean test() {
        return AdditiveAbelianGroup.super.test() && UnitaryRing.super.test();
    }

}
