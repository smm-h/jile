package jile.math.operations;

public interface Concatenation<T> extends AssociativeClosedBinaryOperation<T> {

    /**
     * {@code concatenate(a, b)} = {@code ab} = {@code a+b} = {@code a.b} =
     * {@code a||b} = {@code a⧺b} = {@code a⋅b} = {@code a⌢b},
     */
    @Override
    default public String getSymbol() {
        return "⧺";
    }

    @Override
    default public String getLaTeX() {
        return "\\​doubleplus";
    }

    @Override
    default public BinaryOperation.BinarySymbolPlacement getPlacement() {
        return BinaryOperation.BinarySymbolPlacement.INFIX;
    }

}
