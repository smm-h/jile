package jile.cs;

public class MergeSort<T> extends BaseInplaceSortingAlgorithm<T> {

    public MergeSort() {
        super("merge-sort", true);
    }

    @Override
    public void sort(RearrangeableOrderedStructure<T> s, Measurer<? super T> m) {
        int n = s.size();
        if (n > 1) {
            RearrangeableOrderedStructure<T> L = s.slice(0, n / 2);
            RearrangeableOrderedStructure<T> R = s.slice(n / 2);
            sort(L, m);
            sort(R, m);
            int n_L = L.size();
            int n_R = R.size();
            while (n_L > 0 && n_R > 0)
                s.set(--n, (m.measure(L.get(n_L - 1)) > m.measure(R.get(n_R - 1))) ? L.get(--n_L) : R.get(--n_R));
            while (n_L > 0)
                s.set(--n, L.get(--n_L));
            while (n_R > 0)
                s.set(--n, R.get(--n_R));
        }
    }

    public static void main(String[] args) {
        InplaceSortingAlgorithm.test(new MergeSort<Integer>());
    }
}
