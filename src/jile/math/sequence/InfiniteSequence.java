package jile.math.sequence;

public interface InfiniteSequence extends Sequence {

    @Override
    default boolean isFinite() {
        return false;
    }

    @Override
    default Integer getCardinality() {
        return null;
    }

}
