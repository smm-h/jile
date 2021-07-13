package jile.math.numbers;

public class IntegerInteger extends BaseNumber implements Integer {

    private final int value;

    public IntegerInteger(int value) {
        this.value = value;
    }

    public IntegerInteger(String string) {
        this(java.lang.Integer.parseInt(string));
    }

    @Override
    public double approximate() {
        return value;
    }

    @Override
    public boolean isPrecise() {
        return true;
    }

    public String toString() {
        return java.lang.Integer.toString(value);
    }

    @Override
    public int hashCode() {
        return java.lang.Integer.hashCode(value);
    }

    public boolean equals(Object other) {
        if (other instanceof java.lang.Integer) {
            return value == ((java.lang.Integer) other);
        } else if (other instanceof Real) {
            return value == ((Real) other).approximate();
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Real other) {
        return java.lang.Integer.compare(value, (int) other.approximate());
    }

    @Override
    public String express() {
        return toString();
    }

    @Override
    public Integer add(int other) {
        return Integer.fromContents(other + value);
    }

    @Override
    public Integer multiply(int other) {
        return Integer.fromContents(other * value);
    }

    @Override
    public Integer add(Integer other) {
        if (other instanceof IntegerInteger)
            return Integer.fromContents(((IntegerInteger) other).value + value);
        else
            return Integer.super.add(other);
    }

    @Override
    public Integer multiply(Integer other) {
        if (other instanceof IntegerInteger)
            return Integer.fromContents(((IntegerInteger) other).value * value);
        else
            return Integer.super.multiply(other);
    }
}