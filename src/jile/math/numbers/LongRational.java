package jile.math.numbers;

import jile.common.Common;

public class LongRational extends BaseNumber implements Rational {

    private final long p;
    private final long q;

    public LongRational(int value) {
        this((long) value);
    }

    public LongRational(long value) {
        this((long) value, 1);
    }

    public LongRational(int numerator, int denominator) {
        this((long) numerator, (long) denominator);
    }

    public LongRational(String string) {
        // TODO
        this(Long.parseLong(string), 1);
    }

    public LongRational(long numerator, long denominator) {
        if (denominator == 0)
            throw new IllegalArgumentException("denominator cannot be zero");

        // simplify/reduce upon construction
        long gcd = Common.gcd(numerator, denominator);
        p = numerator / gcd;
        q = denominator / gcd;
    }

    @Override
    public double approximate() {
        return ((double) p) / q;
    }

    @Override
    public Integer getNumerator() {
        return Integer.fromContents(p);
    }

    @Override
    public Integer getDenominator() {
        return Integer.fromContents(q);
    }

    @Override
    public LongRational reciprocal() {
        return new LongRational(q, p);
    }

    @Override
    public LongRational negate() {
        return new LongRational(-p, q);
    }

    @Override
    public LongRational add(Rational other) {
        long p2 = (long) other.getNumerator().approximate();
        long q2 = (long) other.getDenominator().approximate();
        return new LongRational(p * q2 + p2 * q, q * q2);
    }

    @Override
    public LongRational multiply(Rational other) {
        long p2 = (long) other.getNumerator().approximate();
        long q2 = (long) other.getDenominator().approximate();
        return new LongRational(p * p2, q * q2);
    }

    @Override
    public String toString() {
        // return Double.toString(doubleValue());
        return Long.toString(p) + "/" + Long.toString(q);
    }

}
