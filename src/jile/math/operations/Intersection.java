package jile.math.operations;

import jile.math.settheory.SpecificSet;

public interface Intersection<T, S extends SpecificSet<T>> extends ClosedBinaryOperation<S> {

    @Override
    default public String getSymbol() {
        return "∩";
    }

    @Override
    default public BinaryOperation.BinarySymbolPlacement getPlacement() {
        return BinaryOperation.BinarySymbolPlacement.INFIX;
    }

}
