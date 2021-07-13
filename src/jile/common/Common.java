package jile.common;

import java.util.Iterator;

public interface Common {

    public static <T> Iterator<T> makeArrayIterator(T[] array) {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public T next() {
                return array[index++];
            }
        };
    }

    public static <T> Iterable<T> over(Iterator<T> iterator) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return iterator;
            }
        };
    }

    public static <T> Iterable<T> over(T[] array) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return makeArrayIterator(array);
            }
        };
    }

    public static <T> Iterable<T> overImmodifiably(Iterator<T> iterator) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        return iterator.next();
                    }
                };
            }
        };
    }

    public static byte valueOfSymbol(String s) {
        assert s.length() == 1;
        return valueOfSymbol(s.charAt(0));
    }

    public static final byte RADIX_MAX = 36;
    public static final byte RADIX_HEX = 16;
    public static final byte RADIX_DEC = 10;
    public static final byte RADIX_OCT = 8;
    public static final byte RADIX_BIN = 2;

    public static byte valueOfSymbol(char c, byte radix) {
        byte v = valueOfSymbol(c);
        if (v < radix)
            return v;
        else
            throw new IllegalArgumentException("symbol: " + c + " does not match radix: " + radix);
    }

    public static char symbolOfValue(byte value, byte radix) {
        if (value < radix)
            return symbolOfValue(value);
        else
            throw new IllegalArgumentException("value: " + value + " does not match radix: " + radix);
    }

    /**
     * Assumes the given Unicode codepoint is either a digit or a letter and maps it
     * to its base-36 value. Both uppercase and lowercase characters are supported.
     * Any other codepoint will lead to unexpected results.
     */
    public static byte valueOfSymbol(char c) {
        byte value = (byte) c;

        // handles digits
        value -= 48;

        // handles uppercase letters
        if (value >= 17)
            value -= 7;

        // handles lowercase letters
        if (value >= 42)
            value -= 32;

        return value;
    }

    /**
     * Converts {{@code 0}, {@code 1}, {@code 2}, ... {@code 9}, {@code 10},
     * {@code 11}, ... {@code 33}, {@code 34}, {@code 35}} as <i>byte</i> into
     * {{@code '0'}, {@code '1'}, {@code '2'}, ... {@code '9'}, {@code 'A'},
     * {@code 'B'}, ... {@code 'X'}, {@code 'Y'}, {@code 'Z'}} as <i>char</i>.
     */
    public static char symbolOfValue(byte value) {
        assert value >= 0 && value < RADIX_MAX;

        // handles digits
        if (value < 10)
            return (char) (value + 48);

        // handles letters
        else
            return (char) (value + 55);
    }

    public static String stringOfValue(int value) {
        return stringOfValue((long) value, RADIX_DEC);
    }

    public static String stringOfValue(long value, byte radix) {

        String string = "";

        while (value > 0) {
            // System.out.println("[" + digit + "] = [" + symbolOfValue((byte) (value %
            // radix)) + "]");
            string = symbolOfValue((byte) (value % radix)) + string;
            value /= radix;
        }

        if (string.isEmpty())
            return "0";
        else
            return string;
    }

    /**
     * <table>
     * <tr>
     * <th>Input as {@code String}</th>
     * <th>Output as {@code Number}</th>
     * </tr>
     * <tr>
     * <td>"1234"</td>
     * <td>1234</td>
     * </tr>
     * <tr>
     * <td>"0"</td>
     * <td>0</td>
     * </tr>
     * <tr>
     * <td>""</td>
     * <td>0</td>
     * </tr>
     * </table>
     */
    public static Number valueOfString(String string) {
        return valueOfString(string, RADIX_DEC);
    }

    /**
     * <table>
     * <tr>
     * <th>Input as {@code String}</th>
     * <th>Radix as {@code int}</th>
     * <th>Output as {@code Number}</th>
     * </tr>
     * <tr>
     * <td>"ffffff"</td>
     * <td>16</td>
     * <td>16777215</td>
     * </tr>
     * <tr>
     * <td>"11"</td>
     * <td>10</td>
     * <td>11</td>
     * </tr>
     * <tr>
     * <td>"11"</td>
     * <td>2</td>
     * <td>3</td>
     * </tr>
     * </table>
     */
    public static Number valueOfString(String string, byte radix) {

        if (string == null)
            throw new NullPointerException("null has no numeric value");

        if (string.length() == 0)
            throw new NullPointerException("empty string has no numeric value");

        int point = string.indexOf('.');

        int whole;
        if (point == -1)
            whole = string.length();
        else
            whole = point;

        int value = 0;

        for (int i = 0; i < whole; i++) {
            value *= radix;
            value += valueOfSymbol(string.charAt(i));
        }

        if (point == -1) {

            // return int
            return value;
        } else {

            double subvalue = 0;

            for (int i = string.length() - 1; i > point; i--) {
                subvalue += valueOfSymbol(string.charAt(i));
                subvalue /= radix;
            }

            // return double
            return subvalue + value;
        }
    }

    public static String fill(String input, String filler, boolean leftwards, int length, boolean cut) {
        int n = input.length();
        if (n > length) {
            if (cut) {
                if (leftwards) {
                    return input.substring(n - length);
                } else {
                    return input.substring(0, length);
                }
            } else {
                return input;
            }
        } else {
            String addition = filler.repeat((int) Math.ceil((length - n) / filler.length()));
            if (addition.length() > length) {
                addition = addition.substring(0, length);
            }
            if (leftwards) {
                return addition + input;
            } else {
                return input + addition;
            }
        }
    }

    public static String escape(String unescaped) {
        return unescaped.replaceAll("\\\\n", "\n").replaceAll("\\\\t", "\t").replaceAll("\\\\r", "\r")
                .replaceAll("\\\\f", "\f");
    }

    public static String codepointToText(int codepoint) {
        return "U+" + fill(stringOfValue(codepoint, RADIX_HEX), "0", true, 4, false);
    }

    public static int count(String string, char c) {
        int count = 0;
        for (int i = 0; i < string.length(); i++)
            if (string.charAt(i) == c)
                count++;
        return count;
    }

    public static String[] split(String string, char splitter) {

        int n = count(string, splitter) + 1;

        String[] array = new String[n];

        int a, b;

        a = 0;

        for (int i = 0; i < n; i++) {
            b = string.indexOf(splitter, a);
            if (b == -1)
                array[i] = string.substring(a);
            else
                array[i] = string.substring(a, b);
            a = b + 1;
        }

        return array;
    }

    public static double factorial(double n) {
        return n == 0 ? 1 : n * factorial(n - 1);
    }

    public static long factorial(int n) {
        return n == 0 ? 1 : n * factorial(n - 1);
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static int tens(double x) {
        return tens(x, 0);
    }

    private static int tens(double x, int depth) {
        return is_int(x) ? depth : tens(x * 10, depth + 1);
    }

    public static int floor(double x) {
        return (int) x;
    }

    public static int ceil(double x) {
        int f = floor(x);
        return f == x ? f : f + 1;
    }

    public static int round(double x) {
        int f = floor(x);
        return (x - f) < 0.5 ? f : f + 1;
    }

    public static boolean is_int(double x) {
        return floor(x) == x;
    }

    public static float sqrt(float x) {
        return (float) Math.sqrt(x);
    }

    public static double sqrt(double x) {
        return Math.sqrt(x);
    }

    public static float sqrt(int x) {
        return (float) Math.sqrt(x);
    }

    public static int sqr(int x) {
        return x * x;
    }

    public static double sqr(double x) {
        return x * x;
    }

    public static long power(int b, int p) {
        long x = 1;
        for (int i = 0; i < p; i++)
            x *= b;
        return x;
    }

    public static double power(double b, double p) {
        return Math.pow(b, p);
    }

    public static double log(double n, double b) {
        return Math.log(n) / Math.log(b);
    }

    public static boolean isPowerOf(double n, double b) {
        return is_int(log(n, b));
    }

    public static float distance(int x1, int y1, int x2, int y2) {
        return sqrt(sqr(x1 - x2) + sqr(y1 - y2));
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return sqrt(sqr(x1 - x2) + sqr(y1 - y2));
    }

    public static String getExt(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}
