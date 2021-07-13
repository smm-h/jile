package jile.math.arithmetic;

import jile.math.operations.AssociativeClosedBinaryOperation;
import jile.math.operations.BinaryOperation;

/**
 * The {@link GreatestCommonDivisor} of at least two non-zero elements from a
 * {@link CommutativeRing}, like that of integers or polynomials, is another
 * element from that set, which is defined as "the largest such element that
 * divides all others", or is a {@link Divisor} of them.
 */
public interface GreatestCommonDivisor<T> extends AssociativeClosedBinaryOperation<T> {

    @Override
    default public BinaryOperation.BinarySymbolPlacement getPlacement() {
        return BinaryOperation.BinarySymbolPlacement.INFIX;
    }

    @Override
    default public String getSymbol() {
        return "|";
    }

    @Override
    default public T operate(T a, T b) {
        return null;
    }

}
