package jile.common;

import jile.nilex.Setable;

abstract public class Service extends Thread {

    public final Setable<Long> pace = new Setable<Long>(this, "pace", 100L);

    public Service() {
        this(100L);
    }

    public Service(float miliseconds) {
        this((long) miliseconds);
    }

    public Service(long miliseconds) {
        pace.set(miliseconds);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Math.max(5L, pace.get()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (serve())
                break;
        }
    }

    /**
     * return true to stop the service loop, and false to continue
     */
    abstract public boolean serve();
}