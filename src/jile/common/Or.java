package jile.common;

/**
 * The base class for "this or that" object, which holds exactly one object of
 * type either This or That.
 */
abstract public class Or<This, That> {

    abstract public Or<This, That> makeThis(This a);

    abstract public Or<This, That> makeThat(That b);

    abstract public boolean isThis();

    abstract public boolean isThat();

    abstract public This getThis();

    abstract public That getThat();

    @Override
    public int hashCode() {
        if (isThis())
            return getThis().hashCode();
        else
            return getThat().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Or) {
            Or<?, ?> other = (Or<?, ?>) object;
            if (isThis())
                return getThis().equals(other.getThis()) || getThis().equals(other.getThat());
            else
                return getThat().equals(other.getThat()) || getThat().equals(other.getThis());
        } else {
            if (isThis())
                return getThis().equals(object);
            else
                return getThat().equals(object);
        }
    }

    @Override
    public String toString() {
        if (isThis())
            return getThis().toString();
        else
            return getThat().toString();
    }
}
