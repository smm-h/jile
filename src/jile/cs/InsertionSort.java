package jile.cs;

public class InsertionSort<T> extends BaseInplaceSortingAlgorithm<T> {

    public InsertionSort() {
        super("insertion sort", true);
    }

    @Override
    public void sort(RearrangeableOrderedStructure<T> structure, Measurer<? super T> measurer) {
        int n = structure.size();
        for (int i = 1; i < n; ++i) {
            int j = i - 1;
            T key = structure.get(i);
            while (j >= 0 && measurer.measure(structure.get(j)) > measurer.measure(structure.get(i))) {
                structure.set(j + 1, structure.get(j));
                j--;
            }
            structure.set(j + 1, key);
        }
    }
}
