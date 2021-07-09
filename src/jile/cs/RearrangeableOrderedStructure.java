package jile.cs;

/**
 * A {@link RearrangeableOrderedStructure} is an {@link OrderedStructure} whose
 * elements can be re-arranged in-place.
 */
public interface RearrangeableOrderedStructure<T> extends OrderedStructure<T> {

    public void set(int index, T value);

    default public void swap(int a, int b) {
        T temp = get(a);
        set(a, get(b));
        set(b, temp);
    }

    public void beShuffled();

    public void beShuffled(InplaceSortingAlgorithm<T> sortingAlgorithm);

    public Measurer<? super T> getDefaultMeasurer();

    public InplaceSortingAlgorithm<T> getDefaultSortingAlgorithm();

    default public void beSorted() {
        getDefaultSortingAlgorithm().sort(this, getDefaultMeasurer());
    }

    default public void beSorted(Measurer<? super T> measurer) {
        getDefaultSortingAlgorithm().sort(this, measurer);
    }

    default public void beSorted(InplaceSortingAlgorithm<T> sortingAlgorithm) {
        sortingAlgorithm.sort(this, getDefaultMeasurer());
    }

    default public void beSorted(InplaceSortingAlgorithm<T> sortingAlgorithm, Measurer<? super T> measurer) {
        sortingAlgorithm.sort(this, measurer);
    }

    public RearrangeableOrderedStructure<T> slice(int start, int end);

    default public RearrangeableOrderedStructure<T> slice(int start) {
        return slice(start, size());
    }
}
