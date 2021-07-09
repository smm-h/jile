package jile.math.abstractalgebra;

import jile.math.operations.Addition;
import jile.math.operations.AdditiveInversion;
import jile.math.operations.Multiplication;
import jile.math.operations.MultiplicativeInversion;
import jile.math.settheory.SpecificSet;

/**
 * The {@link BaseField} class because it is easier to extend it.
 */
abstract public class BaseField<T> implements Field<T> {

    private final T additiveIdentity;

    private final AdditiveInversion<T> additiveInvertion;

    private final Addition<T> addition;

    private final T multiplicativeIdentity;

    private final MultiplicativeInversion<T> multiplicativeInvertion;

    private final Multiplication<T> multiplication;

    public BaseField(T additiveIdentity, AdditiveInversion<T> additiveInvertion, Addition<T> addition,
            T multiplicativeIdentity, MultiplicativeInversion<T> multiplicativeInvertion,
            Multiplication<T> multiplication) {
        this.additiveIdentity = additiveIdentity;
        this.additiveInvertion = additiveInvertion;
        this.addition = addition;
        this.multiplicativeIdentity = multiplicativeIdentity;
        this.multiplicativeInvertion = multiplicativeInvertion;
        this.multiplication = multiplication;
    }

    @Override
    public T getMultiplicativeIdentity() {
        return multiplicativeIdentity;
    }

    @Override
    public MultiplicativeInversion<T> getMultiplicativeInvertion() {
        return multiplicativeInvertion;
    }

    @Override
    public AdditiveAbelianGroup<T> getGroup() {
        return this;
    }

    @Override
    public Multiplication<T> getMultiplication() {
        return multiplication;
    }

    @Override
    public SpecificSet<T> getSet() {
        return this;
    }

    @Override
    public Addition<T> getOperation() {
        return addition;
    }

    @Override
    public T getIdentity() {
        return additiveIdentity;
    }

    @Override
    public AdditiveInversion<T> getInversion() {
        return additiveInvertion;
    }
}
