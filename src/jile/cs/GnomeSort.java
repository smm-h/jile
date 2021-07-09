package jile.cs;

/**
 * @see https://en.wikipedia.org/wiki/Gnome_sort
 */
public class GnomeSort<T> extends BaseInplaceSortingAlgorithm<T> {

    public GnomeSort() {
        super("gnome sort", true);
    }

    @Override
    public void sort(RearrangeableOrderedStructure<T> structure, Measurer<? super T> measurer) {
        int i = 0;
        while (i < structure.size()) {
            if (i == 0 || measurer.measure(structure.get(i)) >= measurer.measure(structure.get(i - 1))) {
                i++;
            } else {
                structure.swap(i--, i);
            }
        }
    }

    public static void main(String[] args) {
        InplaceSortingAlgorithm.test(new GnomeSort<Integer>());
    }
}
