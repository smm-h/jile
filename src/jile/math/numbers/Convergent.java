package jile.math.numbers;

/**
 * A {@link Convergent} is any object that, through any means, converges towards
 * a specific {@link Real} number.
 * 
 * @see IrrationalConvergent
 */
public interface Convergent {
    public double converge(int depth);
}
