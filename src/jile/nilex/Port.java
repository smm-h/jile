package jile.nilex;

import java.util.HashMap;
import java.util.Map;

/**
 * Ports everywhere must be defined as {@code static} and {@code final}. It is
 * conventional for their title to be the name of their owner class + ':' + name
 * of the property. For example:
 *
 * <blockquote>
 * 
 * <pre>
 * 
 * public class Owner {
 *     public static final Port stuff = new Port("Owner:stuff");
 * }
 * 
 * </pre>
 * 
 * </blockquote>
 */
public class Port<T> {

    private final Map<Code, T> map = new HashMap<Code, T>();

    private final String title;

    public Port(String title) {
        this.title = title;
    }

    public synchronized void write(Code code, T thing) {
        if (thing != null) {
            map.put(code, thing);
        } else {
            throw new NullPointerException("cannot write null on: " + title);
        }
    }

    public synchronized T read(Code code) {
        if (!map.containsKey(code))
            System.out.println("nothing was written on: " + title + ", for: " + code.getIdentity());
        return map.get(code);
    }

    public synchronized boolean exists(Code code) {
        return map.containsKey(code);
    }
}
