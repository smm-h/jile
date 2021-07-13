package jile.common;

// S = source type
// D = destination type
public interface Convertor<S, D> {
    public D convert(S source);
}
