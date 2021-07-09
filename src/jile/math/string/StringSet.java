package jile.math.string;

import jile.math.abstractalgebra.FreeMonoid;
import jile.math.settheory.Set;
import jile.math.settheory.SpecificSet;
import jile.common.Singleton;

/**
 * The {@link StringSet} is the {@link Set} of all strings. It is a
 * {@link FreeMonoid} over the set of all characters ({@link CharacterSet}).
 */
public class StringSet implements FreeMonoid<Character>, Singleton {

    private StringSet() {
    }

    private static StringSet singleton;

    public static StringSet singleton() {
        if (singleton == null) {
            singleton = new StringSet();
        }
        return singleton;
    }

    @Override
    public SpecificSet<Character> getFreeSet() {
        return CharacterSet.singleton();
    }

    public static void main(String[] args) {
        Set set = StringSet.singleton().getSet();
        for (Object i : set.chooseMany(20)) {
            System.out.println(i.toString());
        }
    }
}
