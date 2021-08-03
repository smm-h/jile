package jile.math.settheory.eulerdiagram;

public interface Particular<T> {
    public boolean check(IntrinsicQuality<? extends Particular<T>> q);
}
