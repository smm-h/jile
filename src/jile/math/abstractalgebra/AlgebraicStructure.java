package jile.math.abstractalgebra;

import jile.math.Testable;
import jile.math.operations.Operation;
import jile.math.settheory.Set;

/**
 * An {@link AlgebraicStructure} is a non-empty {@link Set}, some finitary
 * {@link Operation}s, and some identities.
 * 
 * @see Group
 * @see Ring
 * @see BaseField
 * @see Module
 * @see VectorSpace
 * @see Lattice
 * @see Algebra
 * @see Magma
 * @see Loop
 * @see Monoid
 */

public interface AlgebraicStructure extends Testable {
    /**
     * A non-empty set called the underlying set, carrier set or domain
     */
    public Set getSet();

    /**
     * A collection of operations, closed on the underlying set, of finite arity
     */
    public Operation[] getOperations();

    /**
     * A finite set of identities, known as axioms, that the operations satisfy.
     */
    // public Identity[] getIdentities();

}
