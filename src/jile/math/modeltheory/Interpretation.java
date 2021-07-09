package jile.math.modeltheory;

/**
 * In model theory, an {@link Interpretation} of a structure M in another
 * structure N (typically of a different signature) approximates representing M
 * inside N. For example every reduct or expansion of a structure N has an
 * interpretation in N.
 * 
 * Many model-theoretic properties are preserved under interpretability. For
 * example if the theory of N is stable and M is interpretable in N, then the
 * theory of M is also stable.
 * 
 * An interpretation of M in N with parameters (or without parameters,
 * respectively) is a pair (n,f) where n is a natural number and f is a
 * surjective map from a subset of Nn onto M such that the f-preimage (more
 * precisely the }f^-preimage) of every set X ⊆ Mk definable in M by a
 * first-order formula without parameters is definable (in N) by a first-order
 * formula with parameters (or without parameters, respectively). Since the
 * value of n for an interpretation (n,f) is often clear from context, the map f
 * itself is also called an interpretation.
 */
public interface Interpretation {
}
