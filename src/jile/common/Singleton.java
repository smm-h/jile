package jile.common;

/**
 * </p>
 * Any class that implements this interface must expose a static method called
 * {@code singleton} that, if null, instantiates, and returns a single instance
 * of that class. The following code should be used as a snippet.
 * </p>
 * 
 * <blockquote>
 * 
 * <pre>
 * private static CLASS_NAME singleton;
 * 
 * public static CLASS_NAME singleton() {
 *     if (singleton == null) {
 *         singleton = new CLASS_NAME(...);
 *     }
 *     return singleton;
 * }
 * </pre>
 * 
 * </blockquote>
 * <p>
 * The class also must have all of its constructors as private.
 * </p>
 * <p>
 * Any other code that wishes to use this class or "load" it into the JVM,
 * should simply call the {@code singleton} method which both initializes
 * <i>and</i> returns the only instance of it.
 * </p>
 */
public interface Singleton {
}
