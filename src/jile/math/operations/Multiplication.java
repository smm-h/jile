package jile.math.operations;

public interface Multiplication<T> extends AssociativeClosedBinaryOperation<T> {

    @Override
    default public String getSymbol() {
        return "·";
    }

    @Override
    default public BinaryOperation.BinarySymbolPlacement getPlacement() {
        return BinaryOperation.BinarySymbolPlacement.INFIX;
    }

}
