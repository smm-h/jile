package jile.common;

public final class Global {

    private static Global singleton;

    public static Global singleton() {
        if (singleton == null) {
            singleton = new Global();
        }
        return singleton;
    }

    private Global() {
    }
}
