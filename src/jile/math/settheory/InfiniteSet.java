package jile.math.settheory;

public interface InfiniteSet extends Set {

    default public Integer getCardinality() {
        return null;
    }

    default public boolean isFinite() {
        return false;
    }
}
