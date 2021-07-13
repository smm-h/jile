package jile.math.operations;

public interface MultiplicativeInversion<T> extends ClosedUnaryOperation<T> {

    @Override
    default public UnaryOperation.UnarySymbolPlacement getPlacement() {
        return UnaryOperation.UnarySymbolPlacement.BEFORE;
    }

    @Override
    default public String getSymbol() {
        return "1/";
    }

}
