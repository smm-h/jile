package jile.math.settheory.eulerdiagram;

import jile.common.q.Quality;

public interface IntrinsicQuality<T extends Particular<T>> extends Quality<T> {
    @Override
    default public boolean holdsFor(T particular) {
        return particular.check(this);
    }
}
