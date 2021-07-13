package jile.math.operations;

public interface NullaryOperation<Z> extends ClosedOperation<Z> {

    public enum NullarySymbolPlacement implements Operation.SymbolPlacement {
        THERE
    }

    @Override
    default public NullarySymbolPlacement getPlacement() {
        return NullarySymbolPlacement.THERE;
    }

    @Override
    default public int getArity() {
        return 0;
    }

    public Z operate();

    default public Z operateUnchecked() {
        return operate();
    }

}
