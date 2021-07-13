package jile.math.abstractalgebra;

import jile.math.operations.Addition;
import jile.math.operations.AdditiveInversion;

/**
 * An "additive" abelian group is an {@link AbelianGroup} where the binary
 * operation is an {@link Addition}
 */

public interface AdditiveAbelianGroup<T> extends AbelianGroup<T>, AdditiveGroup<T> {

    @Override
    public Addition<T> getOperation();

    @Override
    public AdditiveInversion<T> getInversion();

}
