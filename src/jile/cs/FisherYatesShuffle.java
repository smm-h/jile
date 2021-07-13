package jile.cs;

import java.util.Random;

/**
 * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm
 */
public class FisherYatesShuffle implements InplaceShufflingAlgorithm {
    @Override
    public void shuffle(RearrangeableOrderedStructure<?> structure) {
        Random r = jile.common.Random.singleton();
        int n = structure.size();
        for (int i = n - 1; i > 0; i--)
            structure.swap(i, r.nextInt(i + 1));
    }
}
