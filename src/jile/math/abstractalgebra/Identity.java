package jile.math.abstractalgebra;

import jile.math.expression.Equation;
import jile.math.expression.TreeExpression;

/**
 * An {@link Identity} is an {@link Equation} relating two or more
 * {@link TreeExpression}s, which may contain variables, such that they produce
 * the same value for all values of those variables within a certain range of
 * validity. In other words, an identity is an equality between functions that
 * are differently defined. For example: (a+b)2 ≡ a2+2ab+b2 is an identity.
 * Identities are indicated by ≡ rather than =.
 */
public class Identity extends Equation {

    public Identity(TreeExpression[] sides) {
        super(sides);
    }

}
