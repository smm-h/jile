package jile.cs;

import jile.common.NChronometer;
import jile.common.Chronometer;
import jile.common.Random;
import jile.common.q.Quality;

/**
 * A {@link SortingAlgorithm} is an {@link Algorithm} used to sort
 * {@link OrderedStructure}s.
 * 
 * @see https://en.wikipedia.org/wiki/Sorting_algorithm
 */
public interface SortingAlgorithm<T> extends Algorithm {

    public static final Quality<SortingAlgorithm<?>> stable = new Quality<SortingAlgorithm<?>>() {
        @Override
        public boolean holdsFor(SortingAlgorithm<?> sortingAlgorithm) {
            return sortingAlgorithm.isStable();
        }
    };

    public boolean isStable();

    public static void main(String[] args) {

        int n = 10000;

        Integer[] integers = new Integer[n];

        Filler<Integer> filler = new IntegerFiller();

        for (int i = 0; i < n; i++)
            integers[i] = filler.fill(Random.singleton(), n);

        Array<Integer> array = new ArrayArray<Integer>(integers, new IntegerMeasurer(), new InsertionSort<Integer>());

        System.out.println("ARRAY SIZE:\t" + Integer.toString(array.size()));

        // @formatter:off
        Array<InplaceSortingAlgorithm<Integer>> algorithms = new ArrayArray<InplaceSortingAlgorithm<Integer>>(
            
            new  InsertionSort  <Integer>(),
            new    MergeSort    <Integer>(),
            new  SelectionSort  <Integer>(),
            new   BubbleSort    <Integer>(),
            new    GnomeSort    <Integer>()
            
        );
        // @formatter:on

        algorithms.beShuffled(new BubbleSort<InplaceSortingAlgorithm<Integer>>());

        Chronometer chronometer = new NChronometer();

        InplaceShufflingAlgorithm shuffle = new FisherYatesShuffle();

        for (InplaceSortingAlgorithm<Integer> algorithm : algorithms) {
            // array.beShuffled();
            shuffle.shuffle(array);
            // System.out.println("----------------------------------------------------------------");
            System.out.println(algorithm.toString() + ":");
            // System.out.println();
            // System.out.println(array);
            chronometer.reset();
            array.beSorted((InplaceSortingAlgorithm<Integer>) algorithm);
            double t = chronometer.stop();
            // System.out.println();
            // System.out.println(array);
            // System.out.println();
            System.out.println("\t" + Double.toString(t) + "ms");
        }
    }
}
