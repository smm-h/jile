package jile.math.plotting;

public interface CartesianEquation {
    public double y(double x);

    default public CartesianEquation negate() {
        return x -> -y(x);
    }
}
