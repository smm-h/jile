# Clean APIs with `privately-extends`

- By: SMMH
- Written on: May 26th, 2021

## Abstract

By introducing the keyword `privately-extends` akin to `extends`, we save ourselves a lot of trouble and expose the exact API we want our class to expose, yet remain the same functionality as the classic "public" `extends`.

## Keywords

interface, API, inheritence, compositon, oop

## Introduction

The building block of object-oriented languages, often called "classes", may use other class or themselves in two manners: inheritence and composition, which loosely correspond to "is" and "has" relationships respectively. But not all that a class "has" can be accessed from the outside. This is called "private composition", and anything that _can_ be accessed is called the "API" or "application programming interface" of that class.

"Private inheritance", the concept that this paper aims to introduce, is the inheritance counterpart of the prevalent private composition. Here is a sample Java code that demonstrates this feature:

```java
public class A {
    private X x;
}
```

This makes the field `x` of type `X` visible only throughout the definition of class `A`, and not even its subclasses. This allows us to store and manipulate data in each instance of a class that cannot be set or get. In other words, it is not a part of the API of the class.

## The problem

Unlike composition in which one could choose between `public` or `private` (or other modifiers, depending on the language) for a field, there is no similar construct for inheritance. In other words, when you inherit from a class, you do not have to option to not inherit its complete API, as big and messy as it may be.

Here is a sample Java code that demonstrates the example above, but using inheritance instead of composition:

```java
public class B {
    public X x;
}

public class A extends B {
}
```

Now, `x` is in fact a part of `A`'s API and there is nothing `A` can do about it short of changing `x` to `private` in the definiton of `B`, or privately composing a field `b` of type `B` and using its `x`.

The first solution is not always available since we may inherit from classes that are already compiled or are part of an immutable library. The second solution is also not always a good choice because the framework may be teeming with type checking constructs such as Java's `instanceof` to see if an object is a member of an interface or class, and composing the core class will result in a great deal of confusion and code-rewrite.

## The solution

There is a third solution that solves this API-related problem but unlike the first two solutions, is not readily available in current languages. That solution, is private inheritance.

## An Example in Java

Assume we want to define a new `Expression` class that has a tree-like structure. Sticking to our "best practices", we choose to use our own `Tree` interface which is beautifully implmented using a `LinkedList` in a class called `LinkedTree`.

### 1. [Public] inheritance

The first option is to directly subclass `LinkedTree`:

```java
public class Expression<T> extends LinkedTree<T> {
    // ...
}
```

Effective as it may be, it still leaves a great deal of tree-related API open to our users that have `Expression` objects and may want to tamper with them. This is unwanted and can lead to brittle programs.

### 2. Private composition

The second solution is to have a private field of type `LinkedTree` in our `Expression`:

```java
public class Expression<T> implements TraversibleTree<T> {
    private final LinkedTree<T> tree;
}
```

We can also implement the desired API interface, such as `TraversibleTree`, and delegate all its methods to the privately-held `tree` object.

This acheives our goal which was to have a limited API, but an instance of this class will fail in `instanceof LinkedTree` checks, examples of which may be scattered countlessly in our codebase, or even immutable code such as legacy or libraries. It also forces us to have a ton of unnecessary boilerplate in our class simply to delegate methods to another object. This is a messy and error-prone approach.

### 3. Private inheritance

For `Expression` to extend `LinkedTree`, but does not expose its entire API, it should be able to do so privately, and then publicly "implements" an interface of `LinkedTree` whose API it does want to expose, such as `TraversibleTree`. The following code uses the hypothetical keyword, "`extends-privately`":

```java
public class LinkedTree<T> implements TraversibleTree<T> {
    // ...
}

public class Expression<T> extends-privately LinkedTree<T> implements TraversibleTree<T> {
    // ...
}
```

This both exposes the exact API we wanted, and works correctly with type-checking constructs. It also avoid boilerplate method delegation.

## Conclusion

This feature can, and as demonstrated above should, be added to object-oriented programming languages that care about API exposure, such as Java and C++. Languages that do not however, like Python, need not worry about it, as their class-API is already horribly messy.

This mechanism adds the same precise control over the exposed API by a class to inheritance, that composition has had for the longest time. It is a wonder that it has not already been built.
