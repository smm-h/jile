package jile.math.series;

import jile.math.numbers.Integer;
import jile.math.numbers.Real;

import static jile.common.q.Quality.*;

/**
 * @see https://en.wikipedia.org/wiki/Arithmetic_progression
 */
public interface ArithmeticSeries extends Series<Integer> {

    public Integer getInitialTerm();

    public Integer getDifference();

    @Override
    default public boolean contains(Integer n) {
        return is(n.subtract(getInitialTerm()), Real.Qualities.Multiple.of(getDifference()));
    }

    @Override
    default public Integer getElementAt(int index) {
        return getInitialTerm().add(getDifference().multiply(index));
    }

    @Override
    default public int fairLimit() {
        return 1000;
    }

}
