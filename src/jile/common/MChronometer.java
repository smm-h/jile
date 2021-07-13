package jile.common;

/**
 * A {@link MChronometer} is a {@link Chronometer} that uses
 * {@link System#currentTimeMillis()}. Another chronometer is the
 * {@link NChronometer} that is just as precise, if not more so. That being
 * said, this one is cross-platform and more portable.
 */
public class MChronometer implements Chronometer {

    private double m = 0;

    public void reset() {
        m = System.currentTimeMillis();
    }

    public double stop() {
        return System.currentTimeMillis() - m;
    }
}
