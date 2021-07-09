package jile.math.geometry.d2;

import jile.math.geometry.Point;
import jile.math.numbers.Real;
import jile.math.tuples.*;

/**
 * A {@link TwoDimensionalPoint} is a {@link Point} on a {@link Plane} that is
 * defined a {@link Couple} of {@link Real} numbers.
 */
public class TwoDimensionalPoint implements TwoDimensional, Point, HomogeneousCouple<Real> {

    private final Real x, y;

    public TwoDimensionalPoint(Real x, Real y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Real getFirst() {
        return x;
    }

    @Override
    public Real getSecond() {
        return y;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(16); // 1+6+2+6+1
        builder.append("("); // 1
        builder.append(x.toString()); // 6
        builder.append(", "); // 2
        builder.append(y.toString()); // 6
        builder.append(")"); // 1
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return x.hashCode() ^ y.hashCode();
    }

}
