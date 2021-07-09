package jile.math.annotations;

/**
 * Almost every mathematical structure is meant to be immutable. Given the
 * mutable nature of Java, this gets endangered sometimes in certain interface
 * methods. Annotate those methods so the users of that API may be aware of the
 * risks and be responsible with what they are given by those methods. This
 * annotation also helps remind that certains methods are to be rewritten so
 * they may respect immutability of the structure.
 */
public @interface ImmutabilityEndangered {

}
