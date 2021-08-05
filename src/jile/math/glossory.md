
# Glossory of `jile.math`

[_Go up one level_](readme.md)

## object

Defined in `java.lang.Object`

Any Java object.

## expression

[Defined in `expression.Expression`](expression/Expression.java)

## set

[Defined in `settheory.Set`](settheory/Set.java)

## structure

A "mathematical structure" or simply "structure" consists of a [set](#set), referred to as the underlying set or the domain, with additional features such as [operations](#operation), [relations](#relation), topologies, metrics, etc.

## operand

Any [object](#object) passed to an [operation](#operation) in an [expression](#expression).

## operation

Informally, an operation is "a way in which a number of objects can be used to produce a single object". Formally it is defined as:

$$
\prod_{i=0}^{A} S_i \rightarrow Z
$$

Where $S_i$ and $Z$ are non-empty sets, and $A$ is the number of sets or the "arity" of the operation.

| Case       | Representation                      |
| ---------- | ----------------------------------- |
| Non-closed | $\prod_{i=0}^{A} S_i \rightarrow Z$ |
| Binary     | $S_0×S_1 \rightarrow Z$             |
| Unary      | $S_0 \rightarrow Z$                 |
| Nullary    | $\empty \rightarrow Z$              |

## closure

Closure, closedness, or homogeneousity in the context of relations, is a property of operations.

An operation is to be closed on a set $T$ if and only if $S_i = Z = T$. Thus a closed operation can be represented as such:

$$
\prod_{i=0}^{A} T \rightarrow T
$$

| Case           | Representation                    |
| -------------- | --------------------------------- |
| Closed         | $\prod_{i=0}^{A} T \rightarrow T$ |
| Closed binary  | $T × T \rightarrow T$             |
| Closed unary   | $T \rightarrow T$                 |
| Closed nullary | $\empty \rightarrow T$            |

## arity

Arity is the number of operands an operation needs to be evaluated. Some arities have names:

| Arity            | Name       |
| ---------------- | ---------- |
| $A \ge \aleph_0$ | Infinitary |
| $A \lt \aleph_0$ | Finitary   |
| $A = 2$          | Binary     |
| $A = 1$          | Unary      |
| $A = 0$          | Nullary    |
