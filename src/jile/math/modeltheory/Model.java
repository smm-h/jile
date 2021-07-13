package jile.math.modeltheory;

/**
 * A {@link Model} is an {@link Interpretation} of a {@link Theory} that
 * satisfies its sentences.
 */
public interface Model extends Interpretation {
    public Theory getTheory();
}
