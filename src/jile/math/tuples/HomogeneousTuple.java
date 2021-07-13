package jile.math.tuples;

import jile.math.sequence.SpecificSequence;

/**
 * A {@link HomogeneousTuple} is a {@link Tuple} whose elements are all objects
 * of a specific type.
 * 
 * @see SpecificSequence
 * @see HomogeneousFiniteTuple
 */
public interface HomogeneousTuple<T> extends Tuple, SpecificSequence<T> {
    // public static <T> HomogeneousTuple<T> fromArray(T[] array) {
    // switch (array.length) {
    // case 0:
    // return (HomogeneousTuple<T>) EmptyTuple.singleton();
    // case 1:
    // return new HomogeneousCouple<T>() {

    // };
    // case 2:
    // return getThird();
    // case 3:
    // return getFourth();
    // case 4:
    // return getFifth();
    // case 5:
    // return getSixth();
    // case 6:
    // return getSeventh();
    // case 7:
    // return getEighth();
    // case 8:
    // return getNinth();
    // case 9:
    // return getTenth();
    // case 10:
    // return getEleventh();
    // case 11:
    // return getTwelveth();
    // case 12:
    // return getThirteenth();
    // default:
    // throw new IndexOutOfBoundsException("trying to access index: " + index + " in
    // a 13-tuple");
    // }
    // }
}
