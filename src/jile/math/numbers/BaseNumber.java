package jile.math.numbers;

abstract public class BaseNumber extends Number implements Real {

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return approximate();
    }

    @Override
    public boolean isPrecise() {
        return true;
    }

    public String toString() {
        return Double.toString(approximate());
    }

    @Override
    public int hashCode() {
        return Double.hashCode(approximate());
    }

    public boolean equals(Object other) {
        if (other instanceof Number) {
            return approximate() == ((Number) other).doubleValue();
        } else if (other instanceof Real) {
            return approximate() == ((Real) other).approximate();
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Real other) {
        return Double.compare(approximate(), other.approximate());
    }

    @Override
    public String express() {
        return toString();
    }
}