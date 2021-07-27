package jile.math.booleanalgebra;

import java.util.Iterator;

import jile.math.settheory.*;
import jile.common.Random;
import jile.common.Singleton;

/**
 * The {@link BooleanSet} is the non-nullible finite universal set of all
 * Boolean values, i.e. true and false, a.k.a. truth and falsity.
 */
public class BooleanSet implements FiniteUniversalSet<Boolean>, Singleton {

    private BooleanSet() {
    }

    private static BooleanSet singleton;

    public static BooleanSet singleton() {
        if (singleton == null) {
            singleton = new BooleanSet();
        }
        return singleton;
    }

    @Override
    public Integer getCardinality() {
        return 2;
    }

    @Override
    public java.lang.Boolean choose() {
        return Random.singleton().nextBoolean();
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new Iterator<Boolean>() {

            int elementsLeft = 2;

            @Override
            public boolean hasNext() {
                return elementsLeft > 0;
            }

            @Override
            public Boolean next() {
                switch (elementsLeft--) {
                    case 2:
                        return false;
                    case 1:
                        return true;
                    default:
                        return null;
                }
            }
        };
    }

    public static void main(String[] args) {
        System.out.println(BooleanSet.singleton().excerpt());
    }
}