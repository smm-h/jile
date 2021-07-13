package jile.math.abstractalgebra;

import jile.math.operations.Multiplication;
import jile.math.operations.MultiplicativeInversion;

/**
 * A "multiplicative" abelian group is an {@link AbelianGroup} where the binary
 * operation is an {@link Multiplication}
 */

public interface MultiplicativeAbelianGroup<T> extends AbelianGroup<T>, MultiplicativeGroup<T> {

    @Override
    public Multiplication<T> getOperation();

    @Override
    public MultiplicativeInversion<T> getInversion();

}
