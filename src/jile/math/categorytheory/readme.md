# Category theory [[w](https://en.wikipedia.org/wiki/Category_theory)]

Originated from amidst [algebraic topolgy](TODO) with the goal of understanding the processes that preserve structure, category theory allows a unified way for expressing properties and constructions that are similar for various [algebraic structures](../abstractalgebra/readme.md).

## Category [[w](https://en.wikipedia.org/wiki/Category_(mathematics))]

An **abstract** category is a **labeled directed graph** whose nodes are called **objects**, and whose **labelled directed edges** are called **morphisms**.

A category has two basic properties:

- the ability to compose the morphisms associatively
- and the existence of an identity morphism for each object

Well-known categories are denoted by a short capitalized word or abbreviation in bold or italics: examples include **Set**, the category of sets and set functions; **Ring**, the category of rings and ring homomorphisms; and **Top**, the category of topological spaces and continuous maps.

All of the preceding categories have the identity map as identity morphisms and composition as the associative operation on morphisms.

## Morphisms

A morphism is a structure-preserving map from one mathematical structure to another one of the same type.

| Theory         | Morphism               |
| -------------- | ---------------------- |
| Set theory     | Functions              |
| Linear algebra | Linear transformations |
| Vector spaces  | Linear map             |
| Group theory   | Group homomorphisms    |
| Topology       | Continuous functions   |

A morphism in category theory is an abstraction of a homomorphism.

## Concrete category [[w](https://en.wikipedia.org/wiki/Concrete_category)]

In a concrete category objects are simply sets with some additional structure, and morphisms are structure-preserving functions.

A concrete category is a category that is equipped with a faithful functor to another category, most commonly the category of sets.

This functor makes it possible to think of the objects of the category as sets with additional structure, and of its morphisms as structure-preserving functions. Many important categories have obvious interpretations as concrete categories, for example the category of topological spaces and the category of groups, and trivially also the category of sets itself. On the other hand, the homotopy category of topological spaces is not concretizable, i.e. it does not admit a faithful functor to the category of sets.

A concrete category, when defined without reference to the notion of a category, consists of a class of objects, each equipped with an underlying set; and for any two objects A and B a set of functions, called morphisms, from the underlying set of A to the underlying set of B. Furthermore, for every object A, the identity function on the underlying set of A must be a morphism from A to A, and the composition of a morphism from A to B followed by a morphism from B to C must be a morphism from A to C.[1]
