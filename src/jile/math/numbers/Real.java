package jile.math.numbers;

import jile.math.expression.Expression;
import jile.common.Cacheable;
import jile.common.Common;

/**
 * Since real numbers cannot always be accuarately stored in computers, this
 * interface will be implemented by objects that represent various real numbers
 * accurately, and they may be approximated using doubles. This approximation is
 * either accurate, close-enough, or infinity if the actual number is too
 * {@link Big} to fit in double.
 */
public interface Real extends Expression, Comparable<Real>, Cacheable<Double> {

    public final static Real ZERO = new IntegerInteger(0);
    public final static Real ONE = new IntegerInteger(1);
    public final static Real TWO = new IntegerInteger(2);
    public final static Real M_ONE = new IntegerInteger(-1);
    public final static Real THREE = new IntegerInteger(3);
    public final static Real M_TWO = new IntegerInteger(-2);

    public static Real fromContents(double contents) {
        return DoubleReal.fromContents(contents);
    }

    @Override
    default public Double evaluate() {
        return approximate();
    }

    @Override
    default Double getCached() {
        return approximate();
    }

    public double approximate();

    public boolean isPrecise();

    @Override
    default public int compareTo(Real other) {
        return Double.compare(approximate(), other.approximate());
    }

    public static boolean equals(Real self, Object other) {
        if (self == null || other == null) {
            return false;
        } else if (other instanceof Real) {
            return equals(self, (Real) other);
        } else if (other instanceof Number) {
            return equals(self, (Number) other);
        } else {
            return false;
        }
    }

    public static boolean equals(Real self, Real other) {
        return self.approximate() == other.approximate();
    }

    public static boolean equals(Real self, Number other) {
        return self.approximate() == other.doubleValue();
    }

    public static Real add(Real x, Real y) {
        if (x.isPrecise() && y.isPrecise()) {
            return Real.fromContents(x.approximate() + y.approximate());
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    public static Real subtract(Real x, Real y) {
        if (x.isPrecise() && y.isPrecise()) {
            return Real.fromContents(x.approximate() - y.approximate());
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    public static Real multiply(Real x, Real y) {
        if (x.isPrecise() && y.isPrecise()) {
            return Real.fromContents(x.approximate() * y.approximate());
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    public static Real divide(Real x, Real y) {
        if (x.isPrecise() && y.isPrecise()) {
            double n = x.approximate();
            double d = y.approximate();
            long tens = Common.power(10, Math.max(Common.tens(n), Common.tens(d)));
            return new LongRational((long) (n * tens), (long) (d * tens));
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    public static Real sqr(Real x) {
        if (x.isPrecise()) {
            return Real.fromContents(Common.sqr(x.approximate()));
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    public static Real sqrt(Real x) {
        if (x.isPrecise()) {
            return Real.fromContents(Common.sqrt(x.approximate()));
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }
}
