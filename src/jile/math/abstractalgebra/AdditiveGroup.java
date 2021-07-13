package jile.math.abstractalgebra;

import jile.math.operations.Addition;
import jile.math.operations.AdditiveInversion;

/**
 * An "additive" group is a {@link Group} where the binary operation is an
 * {@link Addition}
 */

public interface AdditiveGroup<T> extends Group<T> {

    @Override
    public Addition<T> getOperation();

    @Override
    public AdditiveInversion<T> getInversion();

}
