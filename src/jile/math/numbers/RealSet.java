package jile.math.numbers;

import jile.math.settheory.NamedSet;
import jile.math.settheory.NonNullibleInfiniteUniversalSet;
import jile.common.Random;

public class RealSet implements NamedSet, NonNullibleInfiniteUniversalSet<Double> {

    private RealSet() {
    }

    private static RealSet singleton;

    public static RealSet singleton() {
        if (singleton == null) {
            singleton = new RealSet();
        }
        return singleton;
    }

    @Override
    public String getName() {
        return "R";
    }

    @Override
    public Double choose() {
        return Math.sqrt(Random.singleton().nextInt() / 100.0);
    }
}
