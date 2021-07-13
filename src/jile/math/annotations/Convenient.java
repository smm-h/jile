package jile.math.annotations;

/**
 * A method annotated with {@link Convenient} is called a "convenience" method,
 * and only exists to pass the exact arguments it gets to another internal
 * object. Please do not annotate your method with it if you change the
 * arguments.
 */
public @interface Convenient {

}
