package jile.math.operations;

/**
 * A {@link ClosedOperation} is an {@link Operation} that takes and gives
 * elements from a set and is, in other words, "closed over" that set.
 */
public interface ClosedOperation<T> extends Operation {

    @Override
    default public boolean isClosed() {
        return true;
    }

    /**
     * operate director
     */
    default public T operate(T[] objects) {
        if (getArity() == objects.length) {
            switch (getArity()) {
            case 0:
                return ((NullaryOperation<T>) this).operate();
            case 1:
                return ((ClosedUnaryOperation<T>) this).operate(objects[0]);
            case 2:
                return ((ClosedBinaryOperation<T>) this).operate(objects[0], objects[1]);
            default:
                throw new UnsupportedOperationException("undefined arity");
            }
        } else {
            throw new UnsupportedOperationException("wrong number of arguments provided");
        }
    }
}
