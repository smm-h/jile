package jile.cs;

/**
 * An {@link Algorithm} is a generic means to a specific end. It is a feasible
 * solution to a problem, even though it may be computationally intensive. In
 * this sense it is in contrast with a {@link Heuristic} in which practicallity
 * of the solution matters more than its optimality. Algorithms can be analyzed
 * for their temporal and spatial complexities.
 * 
 * <h2>Expression</h2> Algorithms are unambiguous finite sequence of
 * instructions that can be expressed in various ways; however this type is
 * meant for only those that are expressed using valid Java code.
 * 
 * @see Heuristic
 */
public interface Algorithm {

    default public double getSpatialComplexity(int n) {
        return 1.0;
    }

    default public double getTemporalComplexity(int n) {
        return 1.0;
    }

    default public double getBestSpatialComplexity(int n) {
        return getSpatialComplexity(n);
    }

    default public double getBestTemporalComplexity(int n) {
        return getTemporalComplexity(n);
    }

    default public double getAverageSpatialComplexity(int n) {
        return getSpatialComplexity(n);
    }

    default public double getAverageTemporalComplexity(int n) {
        return getTemporalComplexity(n);
    }

    default public double getWorstSpatialComplexity(int n) {
        return getSpatialComplexity(n);
    }

    default public double getWorstTemporalComplexity(int n) {
        return getTemporalComplexity(n);
    }
}
