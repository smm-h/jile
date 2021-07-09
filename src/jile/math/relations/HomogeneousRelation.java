package jile.math.relations;

/**
 * A {@link HomogeneousRelation} is an {@link Relation} that takes and gives
 * elements from a set and is, in other words, "closed over" that set.
 */
public interface HomogeneousRelation<T> extends FinitaryRelation {

    @Override
    default public boolean isHomogeneous() {
        return true;
    }

    /**
     * holds director
     */
    default public boolean holds(T[] objects) {
        if (getArity() == objects.length) {
            switch (getArity()) {
            case 1:
                return ((UnaryRelation<T>) this).holds(objects[0]);
            case 2:
                return ((HomogeneousBinaryRelation<T>) this).holds(objects[0], objects[1]);
            case 3:
                return ((HomogeneousTernaryRelation<T>) this).holds(objects[0], objects[1], objects[2]);
            default:
                throw new UnsupportedOperationException("undefined arity");
            }
        } else {
            throw new UnsupportedOperationException("wrong number of arguments provided");
        }
    }
}
