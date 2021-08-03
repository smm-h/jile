package jile.math.settheory;

public class StoredUniversalSet<T> extends StoredSet<T> implements FiniteUniversalSet<T> {

    public StoredUniversalSet(Iterable<T> elements) {
        super(elements);
    }

    public StoredUniversalSet() {
        super();
    }

    @Override
    public Integer getCardinality() {
        return super.getCardinality();
    }
}
