package jile.math.operations;

public interface ScalarMultiplication<A, B, Z> extends BinaryOperation<A, B, Z> {

    @Override
    default public String getSymbol() {
        return "×";
    }

    @Override
    default public BinaryOperation.BinarySymbolPlacement getPlacement() {
        return BinaryOperation.BinarySymbolPlacement.INFIX;
    }
}
