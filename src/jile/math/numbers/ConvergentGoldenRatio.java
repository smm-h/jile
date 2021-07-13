package jile.math.numbers;

/**
 * A {@link ConvergentGoldenRatio} is a {@link Convergent} that, rather slowly,
 * converges to the {@link GoldenRatio}.
 */
public class ConvergentGoldenRatio extends BaseIrrationalConvergent implements GoldenRatio {

    private ConvergentGoldenRatio() {
        super(1000);
    }

    @Override
    public double converge(int depth) {
        return depth == 0 ? 1 : 1 + 1 / converge(depth - 1);
    }

    public static void main(String[] args) {
        System.out.println(new ConvergentGoldenRatio().approximate());
    }
}
