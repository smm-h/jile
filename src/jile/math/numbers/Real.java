package jile.math.numbers;

import jile.math.expression.Expression;
import jile.math.operations.IntegerModulo;
import jile.math.operations.Modulo;
import jile.common.Cacheable;
import jile.common.Common;
import jile.common.q.Quality;

import static jile.common.q.Quality.*;

/**
 * Since real numbers cannot always be accuarately stored in computers, this
 * interface will be implemented by objects that represent various real numbers
 * accurately, and they may be approximated using doubles. This approximation is
 * either accurate, close-enough, or infinity if the actual number is too
 * {@link Big} to fit in double.
 */
public interface Real extends Expression, Comparable<Real>, Cacheable<Double> {

    public final static Integer ZERO = new IntegerInteger(0);
    public final static Integer ONE = new IntegerInteger(1);
    public final static Integer TWO = new IntegerInteger(2);
    public final static Integer M_ONE = new IntegerInteger(-1);
    public final static Integer THREE = new IntegerInteger(3);
    public final static Integer M_TWO = new IntegerInteger(-2);

    public static Real fromContents(Number contents) {
        return fromContents(contents.doubleValue());
    }

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

    default public Real add(Real y) {
        if (isPrecise() && y.isPrecise()) {
            return Real.fromContents(approximate() + y.approximate());
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    default public Real subtract(Real other) {
        if (isPrecise() && other.isPrecise()) {
            return Real.fromContents(approximate() - other.approximate());
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    default public Real multiply(Real other) {
        if (isPrecise() && other.isPrecise()) {
            return Real.fromContents(approximate() * other.approximate());
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    default public Real divide(Number other) {
        return divide(fromContents(other));
    }

    default public Real divide(Real other) {
        if (isPrecise() && other.isPrecise()) {
            double n = approximate();
            double d = other.approximate();
            long tens = Common.power(10, Math.max(Common.tens(n), Common.tens(d)));
            return new LongRational((long) (n * tens), (long) (d * tens));
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    default public Real sqr() {
        if (isPrecise()) {
            return Real.fromContents(Common.sqr(approximate()));
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    default public Real sqrt() {
        if (isPrecise()) {
            return Real.fromContents(Common.sqrt(approximate()));
        } else {
            throw new UnsupportedOperationException("non-precise operation");
        }
    }

    public static Modulo<Integer> modulo = new IntegerModulo();

    default public boolean isInteger() {
        return isPrecise() && Common.is_int(approximate());
    }

    default public Integer toInteger() {
        return (Integer) this;
    }

    public static final class Qualities {
        private Qualities() {
        }

        public static final Quality<Real> whole = new Quality<Real>() {
            @Override
            public boolean holdsFor(Real n) {
                return n.isInteger();
            }
        };

        public static final Quality<Real> even = new Quality<Real>() {
            @Override
            public boolean holdsFor(Real n) {
                return is(n, whole) && modulo.operate(n.toInteger(), TWO.toInteger()).approximate() == 0;
            }
        };

        public static final Quality<Real> odd = new Quality<Real>() {
            @Override
            public boolean holdsFor(Real n) {
                return is(n, whole) && modulo.operate(n.toInteger(), TWO.toInteger()).approximate() != 0;
            }
        };

        public static final Quality<Real> prime = new Quality<Real>() {
            @Override
            public boolean holdsFor(Real n) {
                if (is(n, whole)) {
                    // TODO
                    return true;
                } else {
                    return false;
                }
            }
        };

        public static class Divisible implements Quality<Real> {

            private final Integer d;

            private Divisible(Integer d) {
                this.d = d;
            }

            public static Divisible by(Integer d) {
                return new Divisible(d);
            }

            public static Divisible by(int d) {
                return new Divisible(Integer.fromContents(d));
            }

            @Override
            public boolean holdsFor(Real n) {
                return is(n, whole) && modulo.operate(n.toInteger(), d).approximate() == 0;
            }

        }

        public static interface Multiple {
            public static Divisible of(Integer d) {
                return Divisible.by(d);
            }

            public static Divisible of(int d) {
                return Divisible.by(d);
            }
        }

        public static void main(String[] args) {

            Integer x = new IntegerInteger(30);

            System.out.println(is(x, even));
            System.out.println(is(x, odd));
            System.out.println(is(x, prime));
            System.out.println(is(x, Divisible.by(3)));
            System.out.println(is(x, Divisible.by(4)));
            System.out.println(is(x, Multiple.of(5)));
        }
    }
}
