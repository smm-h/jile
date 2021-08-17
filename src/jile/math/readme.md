# `jile.math`

[_Go up one level_](../readme.md)

## Introduction

### Language

> There is no branch of mathematics, however abstract, which may not some day be applied to phenomena of the real world.
>
> $-$ [Nikolai Ivanovich Lobachevsky](https://en.wikiquote.org/wiki/Nikolai_Ivanovich_Lobachevsky), the "[Copernicus](https://en.wikipedia.org/wiki/Nicolaus_Copernicus) of Geometry"

Though often deemed purely theoretical, mathematics have their roots in human experience and intuition. Individuation of natural automata emergent from particles of matter, and distilling them into atomic concepts that then were transferred throughout space and time via language, laid the groundwork for counting, trade, and arithmetic, for which [numbers](numbers/readme.md) were the written form.

### Generalization

Mathematical theories are nothing but generalizations of either other theories, or ultimately reality; they are agreed upon, fine-tuned, and evolved, rather than ever-perfected. They are the ultimate testament to how absolutely everything is subjective. Mathematics are inventions rather than discoveries. Mathematics is the written form of how great thinkers think.

### Numbers

Individuation led to abstraction, which led to counting, which led to natural numbers. Basic arithmetic necessisated zero and negative numbers, which made up integers. Infinitely-big begged the idea of infinitely small, which created fractions, and ultimately real numbers, and its paradoxes that have accompanied it since antiquity.

### [Geometry](geometry/readme.md)

Unifying the works of his contemperaries like Eudoxus, Euclid developed his _Elements_, which formalized geometry, algorithms, irrational numbers, etc. Named after him, the three-dimensional Euclidean space described our reality.

### Multitudes

See also: [Glossory of `jile.math`](glossory.md)

## The old index

### Set Theory

- [Set theory](settheory/readme.md) [[w](https://en.wikipedia.org/wiki/Set_theory)]
- [`Set`](settheory/Set.java)
- [`BooleanSet`](booleanalgebra/BooleanSet.java) :arrow_forward:
- [`RealSet`](numbers/RealSet.java)
- [`CharacterSet`](string/CharacterSet.java) :arrow_forward:
- [`StringSet`](string/StringSet.java) :arrow_forward:
- [`PureSet`](settheory/PureSet.java) :arrow_forward: [[w](https://en.wikipedia.org/wiki/Von_Neumann_universe)]

### Abstract Algebra

### Series

- [`Series`](series/Series.java)
- [`FibonacciSeries`](series/FibonacciSeries.java)
  - [recursive](series/RecursiveFibonacciSeries.java) :arrow_forward:
- [`FactorialSeries`](series/FactorialSeries.java)
  - [recursive](series/RecursiveFactorialSeries.java) :arrow_forward:
  - [iterative](series/IterativeFactorialSeries.java) :arrow_forward:
- [`GregoryLeibnizSeries`](series/GregoryLeibnizSeries.java)
- [`ChudnovskySeries`](series/ChudnovskySHexas.java)
  - [iterative](series/IterativeFactorialSeries.java) :arrow_forward:

### Convergents

- [`Convergent`](numbers/Convergent.java)
- [`ConvergentPi`](numbers/ConvergentPi.java) :arrow_forward:
- [`ConvergentGoldenRatio`](numbers/ConvergentGoldenRatio.java) :arrow_forward:

### Other

- [`ExpressionParser`](expression/ExpressionParser.java) :arrow_forward:

## The new index

- Notation a way to represent mathematical ideas and objects using symbols [[w](https://en.wikipedia.org/wiki/Mathematical_notation)]
  - `Expression` a finite tree of transformations (this ensures [well-formedness](https://en.wikipedia.org/wiki/Well-formed_formula)) [[w](https://en.wikipedia.org/wiki/Expression_(mathematics))]
    - Formula, any expression whose root is a relation [[w](https://en.wikipedia.org/wiki/Well-formed_formula)]
    - Equation, any expression whose root is a comparation [[w](https://en.wikipedia.org/wiki/Well-formed_formula)]
    - Fraction
    - Polynomial
  - `Expressible`, any mathematical object that can be converted into an expression
  - Generic expressors "express expressibles"
    - `NumeralSystem` a way to produce symbols that represent numbers [[w](https://en.wikipedia.org/wiki/Numeral_system)]
      - `HinduArabicNumeralSystem` [[w](https://en.wikipedia.org/wiki/Hindu%E2%80%93Arabic_numeral_system)]
        - Unary [[w](https://en.wikipedia.org/wiki/Unary_numeral_system)]
        - Binary [[w](https://en.wikipedia.org/wiki/Binary_number)]
        - Decimal [[w](https://en.wikipedia.org/wiki/Decimal)]
        - Hexadecimal [[w](https://en.wikipedia.org/wiki/Hexadecimal)]
      - `RomanNumeralSystem` [[w](https://en.wikipedia.org/wiki/Roman_numerals)]
  - `Symbol`, nodes in an expression tree
    - `Number`, a string of symbols that represents fixed values
    - `Variable`, symbols that represent unknown values
    - Constants, symbols that represent fixed values ($\pi, e$)
    - Delimiters ($(), ||, [], \{\}$)
    - Function symbols ($\sin, \log, \lim$)
    - Operation symbols or operators ($+, -, \times, \div$)
    - Relation symbols
- Algebra the study of symbols and how to manipulate them [[w](https://en.wikipedia.org/wiki/Algebra)]
  - =
    - `Tranformation`, the non-leaf nodes in an expression
  - Elementary- [[w](https://en.wikipedia.org/wiki/Elementary_algebra)]
    - `Variable`, quantities without fixed values
    - `Equation` [[w](https://en.wikipedia.org/wiki/Equation)]
  - Linear-
  - Multilinear-
  - Abstract-
    - `Homology` [[w](https://en.wikipedia.org/wiki/Homology_(mathematics))]
    - `Cohomology` [[w](https://en.wikipedia.org/wiki/Cohomology)]
    - [`Field`](abstractalgebra/Field.java)
      - [`BigNaturalField`](abstractalgebra/BigNaturalField.java) :arrow_forward:
      - [`RationalField`](abstractalgebra/RationalField.java) :arrow_forward:
      - [`RealField`](abstractalgebra/RealField.java) :arrow_forward:
      - [`ComplexField`](abstractalgebra/ComplexField.java) :arrow_forward:
- Geometry [[w](https://en.wikipedia.org/wiki/Geometry)]
  - =
    - `Space` a set of points [[w](https://en.wikipedia.org/wiki/Space_(mathematics))]
      - `Point` "that which has no part." [[w](https://en.wikipedia.org/wiki/Point_(geometry))]
      - `Dimension` the arity of coordinates that identify points within a space [[w](https://en.wikipedia.org/wiki/Dimension)]
      - `Coordinates` a finite tuple used to identify a point in a space [[w](https://en.wikipedia.org/wiki/Coordinate_system)]
    - Metric, generalization of distance [[w](https://en.wikipedia.org/wiki/Metric_(mathematics))]
    - Affinity, preservation of lines and parallelism, but not distance and angle [[w](https://en.wikipedia.org/wiki/Affine_transformation)]
  - Euclidean-
    - =
      - `Axiom` [[w](https://en.wikipedia.org/wiki/Axiom)]
      - `Line` set of points that satisfy a linear equation [[w](https://en.wikipedia.org/wiki/Line_(geometry))]
      - `LineSegment` a subset of a line; "breadthless length" [[w](https://en.wikipedia.org/wiki/Line_segment)]
      - `Angle`, the inclination of two non-parallel lines, which meet, to each other at the point of meeting [[w](https://en.wikipedia.org/wiki/Angle)]
        - Obtuse
        - Right
        - Acute
      - `Plane` set of all points that satisfy a [point-normal form](https://en.wikipedia.org/wiki/Plane_(geometry)#Point%E2%80%93normal_form_and_general_form_of_the_equation_of_a_plane) [[w](https://en.wikipedia.org/wiki/Plane_(geometry))]
      - `Curve` the trail of a moving point [[w](https://en.wikipedia.org/wiki/Curve)]
      - `Surface` [[w](https://en.wikipedia.org/wiki/Surface_(mathematics))]
      - `Manifold` generalizes curve and surface [[w](https://en.wikipedia.org/wiki/Manifold)]
      - Length
      - Area
      - Volume
      - `AffineSpace`, no distance, no angles, no origin point [[w](https://en.wikipedia.org/wiki/Affine_space)]
      - `Incidence` a heterogeneous relation that expresses objects such as points, lines, planes, etc. colliding or lying on top of on another or passing through one another. [[w](https://en.wikipedia.org/wiki/Incidence_(geometry))]
    - Synthetic-
    - Analytic-
    - Algebraic- [[w](https://en.wikipedia.org/wiki/Algebraic_geometry)]
      - `Lemniscate` [[w](https://en.wikipedia.org/wiki/Lemniscate)]
    - Differential- "uses the techniques of differential calculus, integral calculus, linear algebra and multilinear algebra" [[w](https://en.wikipedia.org/wiki/Differential_geometry)]
      - `DifferentiableCurve` [[w](https://en.wikipedia.org/wiki/Differentiable_curve)]
  - Non-Euclidean- [[w](https://en.wikipedia.org/wiki/Non-Euclidean_geometry)]
    - Relaxing metric
      - Kinematic- [[w](https://en.wikipedia.org/wiki/Non-Euclidean_geometry#Kinematic_geometries)]
    - Relaxing affinity
      - Hyperbolic- [[w](https://en.wikipedia.org/wiki/Hyperbolic_geometry)]
      - Elliptic- [[w](https://en.wikipedia.org/wiki/Elliptic_geometry)]
- Topology
  - =
    - `Topology` $\tau$
    - Space
      - `TopologicalSpace` $(X, \tau)$ [[w](https://en.wikipedia.org/wiki/Topological_space)]
      - Compactness [[w](https://en.wikipedia.org/wiki/Compact_space)]
      - Metrizability [[w](https://en.wikipedia.org/wiki/Metrizable_space)]
      - `StoneSpace` [[w](https://en.wikipedia.org/wiki/Stone_space)]
      - Total disconnection [[w](https://en.wikipedia.org/wiki/Totally_disconnected_space)]
    - `Surface` [[w](https://en.wikipedia.org/wiki/Surface_(topology))]
      - Orientability [[w](https://en.wikipedia.org/wiki/Orientability)]
  - Algebraic- "uses tools from abstract algebra" [[w](https://en.wikipedia.org/wiki/Algebraic_topology)]
- Logic
  - Propositional- (zeroth-order-)
    - no variables, no quantifiers
    - `Proposition`
      - can be true or false
      - `AtomicProposition` no connectives
      - `CompoundProposition` connected by connectives
    - `Relation`
      - `Connective`
    - `Argument`
      - `Proof` "sufficient argument for the truth of a proposition"
  - First-order-
    - non-logical objects
    - `Predicate`
    - `Quantifier`

## Objects of Theories

"Mathematics is the study of quantities, structure, space, and change."

- `vis`
  - Elementary Algebra "builds on arithmetic", "symbol-manipulation rules" [[w](https://en.wikipedia.org/wiki/Elementary_algebra)]
    - `Expression`
      - `Variable`
  - `diagrams`
    - `EulerDiagram`
    - `VennDiagram`
    - `RainbowBox` [[w](https://en.wikipedia.org/wiki/Rainbow_box)]
- [`prooftheory`](prooftheory/readme.md)
  - `Proof`
- [`modeltheory`](modeltheory/readme.md)
  - `Model`
- Order theory :book:
- `settheory`
  - `Set`
  - Measure theory :book:
    - `Measure`
- `arithmetic`, Number theory
  - Algebraic-
  - `Number`
    - Transcendental number
  - `Operation`
- `algebra`, "reunion of broken parts" [[w](https://en.wikipedia.org/wiki/Algebra)]
  - `abstract`
    - Invariant theory :book:
    - Representation theory :book:
    - Scheme theory :book:
    - `Structure`
      - Module
      - Ring
      - Semigroup
      - Group
      - Field
      - Algebra over a field [[w](https://en.wikipedia.org/wiki/Algebra_over_a_field)]
  - Commutative- [[w](https://en.wikipedia.org/wiki/Commutative_algebra)]
    - Commutative Algebra [[w](https://en.wikipedia.org/wiki/Algebra_(ring_theory))]
      - Commutative Ring [[w](https://en.wikipedia.org/wiki/Commutative_ring)]
  - Universal-, start from here [[w](https://en.wikipedia.org/wiki/Universal_algebra)]
  - [`categorytheory`](categorytheory/readme.md)
- `analysis`
  - `calculus`
    - Differential-
    - Integral-
  - Real-
  - Complex-
    - `RiemannSurface` [[w](https://en.wikipedia.org/wiki/Riemann_surface)]
  - Functional-
  - `nonstandard` [[w](https://en.wikipedia.org/wiki/Nonstandard_analysis)]
    - `Hyperreal` "A hyperreal $x$ and its [standard part](https://en.wikipedia.org/wiki/Standard_part_function) $st(x)$ are [equal up to](https://en.wikipedia.org/wiki/Up_to) an infinitesimal difference" [[w](https://en.wikipedia.org/wiki/Hyperreal_number)]
    - `Hyperinteger` [[w](https://en.wikipedia.org/wiki/Hyperinteger)]
    - `Infinity` $\omega$ [[w](https://en.wikipedia.org/wiki/Infinity)]
    - `Infinitesimal` $\varepsilon$ [[w](https://en.wikipedia.org/wiki/Infinitesimal)]
  - Approximation theory
  - `Function`, is an `Expression` that has input `Variable`s
  - `Distribution`, generalizes `Function`
- `geometry`
  - Dimensions
  - Algebraic-
    - Dynamical systems
  - Trignometry
- `topology`
  - Algebraic-
  - Differential-
  - Knot
  - Topos [[w](https://en.wikipedia.org/wiki/Topos)]
  - Sheaf
- `cs`
  - `probability`
  - `combinatorics`
    - `BigNumber`
  - `automatatheory`
  - `typetheory`
  - Complexity theory
    - Asymptotic theory
  - Computation theory
    - Recursion
  - `Matrix`
  - `Graph`
    - `ExtremalGraph`
    - `Network`
  - `Queue`
  - Game theory
  - Coding theory
  - Relational database

## The subsystems in Jilic math

- a system that takes in mathematical expressions in the form of non-ambiguous s-expression, as a string, and in turn, either creates the appropriate mathematical object, or finds a previously created one, and returns it

## Renderers

- [KaTeX](https://katex.org/docs/api.html)
