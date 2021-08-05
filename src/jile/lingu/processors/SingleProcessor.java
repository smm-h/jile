package jile.lingu.processors;

abstract public class SingleProcessor implements Processor {

    @Override
    public boolean canBeExtended() {
        return false;
    }

    @Override
    public void extend(Processor processor) {
        throw new UnsupportedOperationException("cannot extend SingleProcessor");
    }

    @Override
    public int getSize() {
        return 1;
    }
}