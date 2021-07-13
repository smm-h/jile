package jile.cs;

/**
 * A {@link InplaceSortingAlgorithm} is a {@link SortingAlgorithm} that happens
 * in-place and mutates the data-structure rather than making a new one.
 * 
 * @see BaseInplaceSortingAlgorithm
 */
public interface InplaceSortingAlgorithm<T> extends SortingAlgorithm<T> {
    public void sort(RearrangeableOrderedStructure<T> structure, Measurer<? super T> measurer);

    public static void test(InplaceSortingAlgorithm<Integer> algorithm) {
        Integer[] a = { 38, 27, 43, 3, 9, 82, 10 };
        RearrangeableOrderedStructure<Integer> s = new ArrayArray<Integer>(a, new IntegerMeasurer(), algorithm);
        System.out.println("BEFORE:");
        System.out.println("\t" + s);
        s.beShuffled();
        System.out.println("AFTER:");
        System.out.println("\t" + s);
    }
}
