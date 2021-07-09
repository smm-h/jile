package jile.cs;

import jile.common.Common;

public class BubbleSort<T> extends BaseInplaceSortingAlgorithm<T> {

    public BubbleSort() {
        super("bubble sort", true);
    }

    @Override
    public void sort(RearrangeableOrderedStructure<T> structure, Measurer<? super T> measurer) {
        int n = structure.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (measurer.measure(structure.get(j)) > measurer.measure(structure.get(j + 1))) {
                    structure.swap(j + 1, j);
                }
            }
        }
    }

    @Override
    public double getTemporalComplexity(int n) {
        return Common.sqr(n);
    }
}
