package jile.common;

import java.time.Clock;
import java.time.Instant;

/**
 * A {@link SCChronometer} is a {@link Chronometer} that uses the
 * {@link Instant}s of any given {@link Clock}.
 */
public class CChronometer implements Chronometer {

    private final Clock clock;

    public CChronometer(Clock clock) {
        this.clock = clock;
    }

    private Instant then = null;

    public void reset() {
        then = clock.instant();
    }

    public double stop() {
        Instant now = clock.instant();
        long difference = then.toEpochMilli() - now.toEpochMilli();
        return difference / 10e6;
    }
}
