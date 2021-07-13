package jile.cs;

public class IntegerMeasurer implements Measurer<Integer> {
    @Override
    public int measure(Integer integer) {
        return integer;
    }
}