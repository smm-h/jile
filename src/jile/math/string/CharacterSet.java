package jile.math.string;

import java.util.Iterator;

import jile.math.settheory.NonNullibleFiniteUniversalSet;
import jile.common.Random;
import jile.common.Singleton;

/**
 * The {@link CharacterSet} is the non-nullible finite universal set of all
 * characters ({@link NonNullibleFiniteUniversalSet}).
 */
public class CharacterSet implements NonNullibleFiniteUniversalSet<Character>, Singleton {

    private CharacterSet() {
    }

    private static CharacterSet singleton;

    public static CharacterSet singleton() {
        if (singleton == null) {
            singleton = new CharacterSet();
        }
        return singleton;
    }

    private final int cardianlity = 96;
    private final int offset = 32;

    @Override
    public Integer getCardinality() {
        return cardianlity;
    }

    @Override
    public Character choose() {
        return (char) (Random.singleton().nextInt(cardianlity) + offset);
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<Character>() {

            int index = offset;

            @Override
            public boolean hasNext() {
                return index < cardianlity + offset;
            }

            @Override
            public Character next() {
                return (char) index++;
            }
        };
    }

    public static void main(String[] args) {
        for (Character i : CharacterSet.singleton()) {
            System.out.println(i.toString());
        }
    }
}
