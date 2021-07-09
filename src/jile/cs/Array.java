package jile.cs;

/**
 * An {@link Array} is a {@link RearrangeableOrderedStructure}.
 */
abstract public class Array<T> implements RearrangeableOrderedStructure<T> {

    private final Measurer<? super T> defaultMeasurer, shuffler;
    private final InplaceSortingAlgorithm<T> defaultSortingAlgorithm;

    public Array(Measurer<? super T> measurer, InplaceSortingAlgorithm<T> sortingAlgorithm) {
        this.defaultMeasurer = measurer;
        this.defaultSortingAlgorithm = sortingAlgorithm;
        this.shuffler = new BaseShuffler();
    }

    @Override
    public Measurer<? super T> getDefaultMeasurer() {
        return defaultMeasurer;
    }

    @Override
    public InplaceSortingAlgorithm<T> getDefaultSortingAlgorithm() {
        return defaultSortingAlgorithm;
    }

    @Override
    public void beShuffled() {
        beShuffled(defaultSortingAlgorithm);
    }

    @Override

    public void beShuffled(InplaceSortingAlgorithm<T> sortingAlgorithm) {
        sortingAlgorithm.sort(this, shuffler);
    }

}