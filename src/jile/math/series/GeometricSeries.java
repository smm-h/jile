package jile.math.series;

import jile.common.Common;
import jile.math.numbers.Real;

/**
 * https://en.wikipedia.org/wiki/Geometric_series
 */
public interface GeometricSeries extends Series<Real> {

    public Real getCoefficient();

    public Real getRatio();

    @Override
    default public boolean contains(Real n) {
        return Common.isPowerOf(n.divide(getCoefficient()), getRatio());
    }

    @Override
    default public Real getElementAt(int index) {
        return getCoefficient() * Common.power(getRatio(), index);
    }

    @Override
    default public int fairLimit() {
        return 100;
    }
}
