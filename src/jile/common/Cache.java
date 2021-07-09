package jile.common;

public interface Cache<Wrapper, Contents> {

    public Wrapper get(Contents contents);

    public void add(Contents contents, Wrapper wrapper) throws CacheCollisionException;

}
