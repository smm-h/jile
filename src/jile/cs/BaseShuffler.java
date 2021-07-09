package jile.cs;

import java.util.Random;

public class BaseShuffler implements Shuffler {

    @Override
    public Random getRandom() {
        return jile.common.Random.singleton();
    }

    public static void main(String[] args) {
        Measurer<? super Integer> shuffler = (Measurer<? super Integer>) new BaseShuffler();
        for (int i = 0; i < 20; i++)
            System.out.println(shuffler.measure(i));
    }
}
