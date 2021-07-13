package jile.common;

/**
 * A {@link NChronometer} is a {@link Chronometer} that uses
 * {@link System#nanoTime()}. Another chronometer is the {@link MChronometer}
 * that is often much less precise.
 */
public class NChronometer implements Chronometer {

    private double n = 0;

    public void reset() {
        n = System.nanoTime();
    }

    public double stop() {
        return (System.nanoTime() - n) / 10e6;
    }
}
