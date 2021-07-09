package jile.math.numbers;

/**
 * A {@link Big} real number is a {@link Real} number that is virtually
 * limitless in the magnitude of the number it represents, unlike primitive data
 * types and their various wrappers. It can be converted to and from that small
 * type, but some information may be lost. This can be checked using
 * {@link #isMinimizeable()}.
 * 
 * @see BigNatural
 * @see BigDecimal
 */
public interface Big extends Real {

    /**
     * @return Whether or not the minimization process would be lossless and
     *         irreversible.
     */
    public boolean isMinimizeable();

    /**
     * {@link Big} numbers are always precise in themselves, and so their precision
     * depends entirely on their minimizeability.
     * 
     * @see #isMinimizeable()
     */
    @Override
    default public boolean isPrecise() {
        return !isMinimizeable();
    }

    // public static <T extends Real> Big<T> add(Big<T> x, Big<T> y) {
    // if (x.isPrecise() && y.isPrecise()) {
    // return Big.fromContents(x.approximate() + y.approximate());
    // } else {
    // throw new UnsupportedOperationException("non-precise operation");
    // }
    // }

    // public static <T extends Real> Big<T> subtract(Big<T> x, Big<T> y) {
    // if (x.isPrecise() && y.isPrecise()) {
    // return Big.fromContents(x.approximate() - y.approximate());
    // } else {
    // throw new UnsupportedOperationException("non-precise operation");
    // }
    // }

    // public static <T extends Real> Big<T> multiply(Big<T> x, Big<T> y) {
    // if (x.isPrecise() && y.isPrecise()) {
    // return Big.fromContents(x.approximate() * y.approximate());
    // } else {
    // throw new UnsupportedOperationException("non-precise operation");
    // }
    // }

    // public static <T extends Real> Big<T> divide(Big<T> x, Big<T> y) {
    // if (x.isPrecise() && y.isPrecise()) {
    // double n = x.approximate();
    // double d = y.approximate();
    // long tens = Common.power(10, Math.max(Common.tens(n), Common.tens(d)));
    // return new LongRational((long) (n * tens), (long) (d * tens));
    // } else {
    // throw new UnsupportedOperationException("non-precise operation");
    // }
    // }

    // public static <T extends Real> Big<T> sqr(Big<T> x) {
    // if (x.isPrecise()) {
    // return Big.fromContents(Common.sqr(x.approximate()));
    // } else {
    // throw new UnsupportedOperationException("non-precise operation");
    // }
    // }

    // public static <T extends Real> Big<T> sqrt(Big<T> x) {
    // if (x.isPrecise()) {
    // return Big.fromContents(Common.sqrt(x.approximate()));
    // } else {
    // throw new UnsupportedOperationException("non-precise operation");
    // }
    // }

}
