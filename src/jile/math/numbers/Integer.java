package jile.math.numbers;

/**
 * An {@link Integer} is any {@link Real} number that can be written without a
 * {@link Fraction}.
 */
public interface Integer extends Real {
    public static Integer fromContents(long contents) {
        return (Integer) Real.fromContents((double) contents);
    }

    public static Integer fromContents(int contents) {
        return (Integer) Real.fromContents((double) contents);
    }
}
