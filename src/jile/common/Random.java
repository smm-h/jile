package jile.common;

public class Random implements Singleton {

    private Random() {
    }

    private static java.util.Random singleton;

    public static java.util.Random singleton() {
        if (singleton == null) {
            singleton = new java.util.Random();
        }
        return singleton;
    }
}
