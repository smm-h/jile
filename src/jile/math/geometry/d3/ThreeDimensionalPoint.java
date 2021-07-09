package jile.math.geometry.d3;

import jile.math.geometry.Point;
import jile.math.numbers.Real;
import jile.math.tuples.*;

/**
 * A {@link ThreeDimensionalPoint} is a {@link Point} in the 3-dimensional
 * {@link EuclideanSpace} that is defined a {@link Triple} of {@link Real}
 * numbers.
 */
public class ThreeDimensionalPoint implements ThreeDimensional, Point, HomogeneousTriple<Real> {

    private final Real x, y, z;

    public ThreeDimensionalPoint(Real x, Real y, Real z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public Real getThird() {
        return z;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(24); // 1+6+2+6+2+6+1
        builder.append("("); // 1
        builder.append(x.toString()); // 6
        builder.append(", "); // 2
        builder.append(y.toString()); // 6
        builder.append(", "); // 2
        builder.append(z.toString()); // 6
        builder.append(")"); // 1
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return x.hashCode() ^ y.hashCode() ^ z.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return Point.equals(this, other);
    }

}
