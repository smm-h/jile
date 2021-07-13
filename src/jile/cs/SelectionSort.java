package jile.cs;

public class SelectionSort<T> extends BaseInplaceSortingAlgorithm<T> {

    public SelectionSort() {
        super("selection sort", false);
    }

    @Override
    public void sort(RearrangeableOrderedStructure<T> structure, Measurer<? super T> measurer) {
        int n = structure.size();
        for (int i = 0; i < n - 1; i++) {
            int minValue = measurer.measure(structure.get(i));
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                int m = measurer.measure(structure.get(j));
                if (m < minValue) {
                    minValue = m;
                    minIndex = j;
                }
            }
            structure.swap(i, minIndex);
        }
    }
}
