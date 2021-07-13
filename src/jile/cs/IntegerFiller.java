package jile.cs;

import java.util.Random;

public class IntegerFiller implements Filler<Integer> {
    @Override
    public Integer fill(Random random, int magnitude) {
        return (int) Math.round(random.nextGaussian() / Math.PI * magnitude * 10);
    }
}