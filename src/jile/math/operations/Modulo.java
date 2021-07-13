package jile.math.operations;

public interface Modulo<T> extends ClosedBinaryOperation<T> {

    @Override
    default public String getSymbol() {
        return "%";
    }

    @Override
    default String getLaTeX() {
        return "\\mod";
    }

    @Override
    default public BinaryOperation.BinarySymbolPlacement getPlacement() {
        return BinaryOperation.BinarySymbolPlacement.INFIX;
    }

}
