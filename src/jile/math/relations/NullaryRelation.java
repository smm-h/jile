package jile.math.relations;

import jile.math.tuples.FiniteTuple;
import jile.math.annotations.Controversial;
import jile.math.tuples.EmptyTuple;

/**
 * @implNote A "homogeneous nullary relation" type does not exist all nullary
 *           relations are implicitly homogeneous, on any set.
 * 
 * @see https://math.stackexchange.com/questions/4178618/is-a-nullary-relation-homogeneous
 */
public interface NullaryRelation extends FinitaryRelation {

    @Override
    default public Integer getArity() {
        return 0;
    }

    /**
     * A nullary relation is always homogeneous.
     */
    @Override
    @Controversial
    default public boolean isHomogeneous() {
        return true;
    }

    public boolean holdsUnchecked();

    @Override
    default public Integer getCardinality() {
        return holdsUnchecked() ? 1 : 0;
    }

    @Override
    default public FiniteTuple choose() {
        return holdsUnchecked() ? EmptyTuple.singleton() : null;
    }
}
