package jile.math.abstractalgebra;

import jile.math.operations.Division;
import jile.math.operations.Multiplication;
import jile.math.operations.MultiplicativeInversion;

/**
 * A {@link UnitaryRing} is a {@link Ring} that has a multiplicative identity
 * and defines a multiplicative inverse. It is also known as a "unital ring", or
 * "ring with identity".
 * <p>
 * A significant number of sources advice that rings and unitary rings be the
 * same since there is not much to gain from this distinction; the following
 * links provide disagreeing arguments compelling enough to believe otherwise:
 * <ol>
 * <li>https://mathoverflow.net/questions/22579/what-are-the-reasons-for-considering-rings-without-identity</li>
 * <li>https://math.stackexchange.com/questions/16168/applications-of-rings-without-identity</li>
 * <li>https://math.stackexchange.com/questions/48587/definition-of-ring-vs-rng</li>
 * </ol>
 */
public interface UnitaryRing<T> extends Ring<T> {

    public T getMultiplicativeIdentity();

    public MultiplicativeInversion<T> getMultiplicativeInvertion();

    @Override
    default public T multiply(T x, T y) {
        return getMultiplication().operate(x, y);
    }

    @Override
    default public T divide(T x, T y) {
        return multiply(x, multiplicativeInverse(y));
    }

    default public T multiplicativeInverse(T x) {
        return getMultiplicativeInvertion().operate(x);
    }

    default public Division<T> makeDivision() {
        return new Division<T>() {

            private Multiplication<T> multiplication = getMultiplication();
            private MultiplicativeInversion<T> multiplicativeInversion = getMultiplicativeInvertion();

            @Override
            public T operate(T a, T b) {
                return multiplication.operate(a, multiplicativeInversion.operate(b));
            }
        };
    }
}
