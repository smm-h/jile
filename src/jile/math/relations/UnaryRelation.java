package jile.math.relations;

import jile.math.operations.ClosedUnaryOperation;

/**
 * @implNote A "homogeneous unary relation" type does not exist because unlike
 *           in operations, a unary relation is already implicitly homogeneous.
 * @see ClosedUnaryOperation
 */
public interface UnaryRelation<A> extends HomogeneousRelation<A> {

    @Override
    default public Integer getArity() {
        return 1;
    }

    public boolean holds(A a);

    @SuppressWarnings("unchecked")
    default public boolean holdsUnchecked(Object a) throws ClassCastException {
        return holds((A) a);
    }

    @Override
    default public boolean isHomogeneous() {
        return true;
    }
}
