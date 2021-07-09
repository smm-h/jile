package jile.math.relations;

public interface BinaryRelation<A, B> extends FinitaryRelation {

    @Override
    default public Integer getArity() {
        return 2;
    }

    public boolean holds(A a, B b);

    @SuppressWarnings("unchecked")
    default public boolean holdsUnchecked(Object a, Object b) throws ClassCastException {
        return holds((A) a, (B) b);
    }
}
