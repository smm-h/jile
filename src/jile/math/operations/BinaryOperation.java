package jile.math.operations;

public interface BinaryOperation<A, B, Z> extends Operation {

    public enum BinarySymbolPlacement implements Operation.SymbolPlacement {
        PREFIX, INFIX, POSTFIX
    }

    @Override
    default public int getArity() {
        return 2;
    }

    public Z operate(A a, B b);

    @SuppressWarnings("unchecked")
    default public Z operateUnchecked(Object a, Object b) throws ClassCastException {
        return operate((A) a, (B) b);
    }

    /**
     * A binary operation may be associative. By default though, it is not.
     * 
     * @see AssociativeClosedBinaryOperation
     */
    default public boolean isAssociative() {
        return false;
    }
}
