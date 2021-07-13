package jile.math.operations;

public interface Division<T> extends ClosedBinaryOperation<T> {

    @Override
    default public String getSymbol() {
        return "÷";
    }

    @Override
    default public BinaryOperation.BinarySymbolPlacement getPlacement() {
        return BinaryOperation.BinarySymbolPlacement.INFIX;
    }

}
