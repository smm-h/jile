package jile.common;

/**
 * A {@link Chronometer} is anything that can somehow "measure the elapsed time"
 * between the invokations of two of its methods: {@code reset} and
 * {@code stop}. It is often used for "stopwatch microbenchmarking", which is
 * only meaningful with "enough" iterations.
 * 
 * <p>
 * The only inaccuracies in this kind of benchmarking is the unwanted OS or JVM
 * code such as GC or other optimizations that may run during our code, most of
 * which can be avoided by passing certain flags to the VM. One can also use JMH
 * or Google's Caliper.
 * 
 * @see MChronometer
 * @see NChronometer
 * @see SCChronometer
 * @see CChronometer
 */
public interface Chronometer {

    /**
     * Resets the chronometer.
     */
    public void reset();

    /**
     * @return The elapsed time since the last "reset", in miliseconds.
     */
    public double stop();
}
