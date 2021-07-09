package jile.math.numbers;

import jile.common.Common;

/**
 * A {@link BigNatural} is a {@link Big}, {@link Natural} number.
 */
public class BigNatural extends BaseNumber implements Big, Natural {

    private final byte radix;
    private final byte[] array;
    private int end;

    public BigNatural(long value, byte radix) {
        this(Common.stringOfValue(value, radix), radix);
    }

    public BigNatural(String digits, byte radix) {
        this.radix = radix;
        int n = digits.length();
        end = n;
        array = new byte[n--];
        for (int i = 0; i <= n; i++)
            array[i] = Common.valueOfSymbol(digits.charAt(n - i), radix);
    }

    private BigNatural(int digits, byte radix) {
        this.radix = radix;
        array = new byte[digits];
        end = 0;
    }

    private BigNatural(BigNatural b) {
        radix = b.radix;
        array = b.array.clone();
        end = b.end;
    }

    public int getLength() {
        return end;
    }

    private byte get(int place) {
        if (place >= end)
            return 0;
        else
            return array[place];
    }

    /**
     * add two big numbers
     */
    public BigNatural add(BigNatural other) {
        int n = Math.max(getLength(), other.getLength()) + 1;
        var b = new BigNatural(n, other.radix);

        int sum;
        byte carry = 0;
        int place = 0;

        while (place < n) {
            sum = get(place) + other.get(place) + carry;
            carry = 0;
            while (sum >= radix) {
                sum -= radix;
                carry++;
            }
            b.array[place++] = (byte) sum;
        }

        b.end = n;
        b.tighten();

        return b;
    }

    private void tighten() {
        while (end > 0 && array[end - 1] == 0)
            end--;
    }

    // private final double lr = Math.log(radix);

    // public BigNatural add(BigNatural... others) {
    // int maxLength = getLength();

    // for (int i = 0; i < others.length; i++)
    // maxLength = Math.max(maxLength, others[i].getLength());

    // return add(others, maxLength + (int) Math.ceil(Math.log(others.length) /
    // lr));
    // }

    /**
     * add many big numbers all at once
     */
    public static BigNatural add(int n, byte radix, BigNatural[] others) {

        var b = new BigNatural(n, radix);

        int sum;
        byte carry = 0;
        int place = 0;

        while (place < n) {
            sum = carry;
            for (int i = 0; i < others.length; i++) {
                sum += others[i].get(place);
            }
            carry = 0;
            while (sum >= radix) {
                sum -= radix;
                carry++;
            }
            b.array[place++] = (byte) sum;
        }

        b.end = n;
        b.tighten();

        return b;
    }

    public BigNatural multiply(int digit, int shift) {
        assert digit < radix;

        int n = getLength() + shift + 1;
        var b = new BigNatural(n, radix);

        int sum;
        byte carry = 0;
        int place = 0;

        while (place < n) {

            if (place < shift) {
                sum = 0;
            } else {
                sum = get(place - shift) * digit + carry;
                carry = 0;
                while (sum >= radix) {
                    sum -= radix;
                    carry++;
                }
            }

            b.array[place++] = (byte) sum;
        }

        b.end = n;
        b.tighten();

        return b;
    }

    public BigNatural multiply(BigNatural other) {

        int n = getLength();

        BigNatural[] a = new BigNatural[n];

        for (int i = 0; i < n; i++)
            a[i] = other.multiply(get(i), i);

        return add(n + other.getLength(), radix, a);
    }

    public String asString() {

        int n = getLength();

        char[] c = new char[n--];

        for (int i = 0; i <= n; i++)
            c[n - i] = Common.symbolOfValue(array[i], radix);

        return new String(c);
    }

    @Override
    public String toString() {
        // return asString();
        return "[" + asString() + "]-(Length: " + getLength() + ")-(Base: " + radix + ")";
    }

    /**
     * For any natural number bigger than zero it returns that number minus one, and
     * for zero itself it returns {@code null}.
     */
    public BigNatural decrement() {
        var b = new BigNatural(this);
        if (b.decrement(0)) {
            b.tighten();
            return b;
        } else {
            return null;
        }
    }

    private boolean decrement(int place) {
        if (place == end) {
            return false;
        } else if (array[place] == 0) {
            if (decrement(place + 1)) {
                array[place] = radix;
            } else {
                return false;
            }
        }
        array[place] -= 1;
        return true;
    }

    public long getValue() {

        int n = getLength();

        long value = 0;

        for (int i = 0; i < n; i++)
            value += array[i] * Math.pow(radix, i);

        return value;
    }

    @Override
    public double approximate() {
        return getValue();
    }

    @Override
    public boolean isMinimizeable() {
        int n = getLength();
        long m = Long.MAX_VALUE;
        while (m > 0) {
            if (n == 0)
                return false;
            m /= radix;
            n--;
        }
        return true;
    }

    public BigNatural factorial() {
        if (getValue() == 1) {
            return this;
        } else {
            return multiply(decrement().factorial());
        }
    }
}
