package jile.math.series;

/**
 * A {@link RecursiveSeries} is a {@link Series}, the calculation of whose
 * elements involve a recursion of typically O(n) where n is the index of the
 * element being calculated. These series are often also {@link ParasiteSeries}.
 * 
 * @implNote Iterative series are preferred to recursive series for high n's,
 *           for which a recursive series may even cause a
 *           {@link StackOverflowError}.
 * 
 * @see IterativeSeries
 */
public interface RecursiveSeries extends Series {

}
