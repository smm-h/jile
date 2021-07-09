package jile.common;

/** Takes the same size in memory, but does one unchecked cast per get */
public class SlimOr<This, That> extends Or<This, That> implements Singleton {

    private static SlimOr<?, ?> singleton;

    public static SlimOr<?, ?> singleton() {
        if (singleton == null) {
            singleton = new SlimOr<Object, Object>(null, false);
        }
        return singleton;
    }

    private final Object object;
    private final boolean isThis;

    private SlimOr(Object object, boolean isThis) {
        this.object = object;
        this.isThis = isThis;
    }

    @Override
    public SlimOr<This, That> makeThis(This object) {
        return new SlimOr<This, That>(object, true);
    }

    @Override
    public SlimOr<This, That> makeThat(That object) {
        return new SlimOr<This, That>(object, false);
    }

    @Override
    public boolean isThis() {
        return isThis;
    }

    @Override
    public boolean isThat() {
        return !isThis;
    }

    @SuppressWarnings("unchecked")
    @Override
    public This getThis() {
        return (This) object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public That getThat() {
        return (That) object;
    }
}
