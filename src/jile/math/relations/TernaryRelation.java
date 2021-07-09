package jile.math.relations;

public interface TernaryRelation<A, B, C> extends FinitaryRelation {

    @Override
    default public Integer getArity() {
        return 2;
    }

    public boolean holds(A a, B b, C c);

    @SuppressWarnings("unchecked")
    default public boolean holdsUnchecked(Object a, Object b, Object c) throws ClassCastException {
        return holds((A) a, (B) b, (C) c);
    }
}
