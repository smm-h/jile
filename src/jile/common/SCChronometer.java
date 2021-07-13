package jile.common;

import java.time.Instant;

/**
 * A {@link SCChronometer} is a {@link Chronometer} that uses the
 * {@link Instant}s of the system clock. For a more general approach see
 * {@link CChronometer} that uses the instants of any given clock.
 */
public class SCChronometer implements Chronometer {

    private Instant then = null;

    public void reset() {
        then = Instant.now();
    }

    public double stop() {
        Instant now = Instant.now();
        long difference = then.toEpochMilli() - now.toEpochMilli();
        return difference / 10e6;
    }
}
