package jile.cs;

abstract public class BaseInplaceSortingAlgorithm<T> implements InplaceSortingAlgorithm<T> {

    private final String name;
    private final boolean stable;

    public BaseInplaceSortingAlgorithm(String name, boolean stable) {
        this.name = name;
        this.stable = stable;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean isStable() {
        return stable;
    }
}
