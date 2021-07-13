package jile.math.annotations;

/**
 * A method annotated with {@link QualityTest} is called a "quality checker"
 * method, and must not take any arguments. It merely evaluates internally
 * accessible data to return a boolean which, if false, means failure. A true
 * value however, does not mean success. In other words, a false-psoitive is
 * possible. Please do not annotate your method with it if it takes arguments.
 */
public @interface QualityTest {

}
