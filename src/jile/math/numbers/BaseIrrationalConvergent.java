package jile.math.numbers;

abstract public class BaseIrrationalConvergent extends BaseNumber implements IrrationalConvergent {

    private final double convergence;

    public BaseIrrationalConvergent(int depth) {
        convergence = converge(depth);
    }

    @Override
    public double approximate() {
        return convergence;
    }

    @Override
    public boolean isPrecise() {
        return false;
    }
}
