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

Unifying the works of his contemperaries, Euclid developed his _Elements_, which formalized geometry, algorithms, irrational numbers, etc. Named after him, the three-dimensional Euclidean space described our reality.

### Multitudes

See also: [Glossory of `jile.math`](glossory.md)

## The old index

### Set Theory

- [Set theory](settheory/readme.md) <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Set_theory)]</sup>
- [`Set`](settheory/Set.java)
- [`BooleanSet`](booleanalgebra/BooleanSet.java) :arrow_forward:
- [`RealSet`](numbers/RealSet.java)
- [`CharacterSet`](string/CharacterSet.java) :arrow_forward:
- [`StringSet`](string/StringSet.java) :arrow_forward:
- [`PureSet`](settheory/PureSet.java) :arrow_forward: <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Von_Neumann_universe)]</sup>

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
- [Model theory](modeltheory/readme.md)

## The new index

- Notation a way to represent mathematical ideas and objects using symbols <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Mathematical_notation)]</sup>
  - `Expression` a finite tree of transformations (this ensures [well-formedness](https://en.wikipedia.org/wiki/Well-formed_formula)) <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Expression_(mathematics))]</sup>
    - Formula, any expression whose root is a relation <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Well-formed_formula)]</sup>
    - Equation, any expression whose root is a comparation <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Well-formed_formula)]</sup>
    - Fraction
    - Polynomial
  - `Expressible`, any mathematical object that can be converted into an expression
  - Generic expressors "express expressibles"
    - `NumeralSystem` a way to produce symbols that represent numbers <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Numeral_system)]</sup>
      - `HinduArabicNumeralSystem` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Hindu%E2%80%93Arabic_numeral_system)]</sup>
        - Unary <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Unary_numeral_system)]</sup>
        - Binary <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Binary_number)]</sup>
        - Decimal <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Decimal)]</sup>
        - Hexadecimal <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Hexadecimal)]</sup>
      - `RomanNumeralSystem` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Roman_numerals)]</sup>
  - `Symbol`, nodes in an expression tree <sup>[[Wikipedia]()]</sup>
    - `Number`, a string of symbols that represents fixed values <sup>[[Wikipedia]()]</sup>
    - `Variable`, symbols that represent unknown values <sup>[[Wikipedia]()]</sup>
    - Constants, symbols that represent fixed values ($\pi, e$)
    - Delimiters ($(), ||, [], \{\}$)
    - Function symbols ($\sin, \log, \lim$)
    - Operation symbols or operators ($+, -, \times, \div$)
    - Relation symbols
- Algebra the study of symbols and how to manipulate them <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Algebra)]</sup>
  - =
    - `Tranformation`, the non-leaf nodes in an expression
  - Elementary- <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Elementary_algebra)]</sup>
    - `Variable`, quantities without fixed values
    - `Equation` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Equation)]</sup>
  - Linear- <sup>[[Wikipedia]()]</sup>
  - Multilinear- <sup>[[Wikipedia]()]</sup>
  - Abstract- <sup>[[Wikipedia]()]</sup>
    - `Homology` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Homology_(mathematics))]</sup>
    - `Cohomology` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Cohomology)]</sup>
    - [`Field`](abstractalgebra/Field.java)
      - [`BigNaturalField`](abstractalgebra/BigNaturalField.java) :arrow_forward:
      - [`RationalField`](abstractalgebra/RationalField.java) :arrow_forward:
      - [`RealField`](abstractalgebra/RealField.java) :arrow_forward:
      - [`ComplexField`](abstractalgebra/ComplexField.java) :arrow_forward:
- Geometry <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Geometry)]</sup>
  - =
    - [`Space`] set of points
    - `Space#Point` "that which has no part." <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Point_(geometry))]</sup>
    - Metric, generalization of distance <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Metric_(mathematics))]</sup>
    - Affinity, preservation of lines and parallelism, but not distance and angle <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Affine_transformation)]</sup>
  - Euclidean- <sup>[[Wikipedia]()]</sup>
    - =
      - `Axiom` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Axiom)]</sup>
      - `Dimension` <sup>[[Wikipedia]()]</sup>
      - `Line` set of points that satisfy a linear equation <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Line_(geometry))]</sup>
      - `LineSegment` a subset of a line; "breadthless length" <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Line_segment)]</sup>
      - `Angle`, the inclination of two non-parallel lines, which meet, to each other at the point of meeting <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Angle)]</sup>
        - Obtuse
        - Right
        - Acute
      - `Plane` set of all points that satisfy a [point-normal form](https://en.wikipedia.org/wiki/Plane_(geometry)#Point%E2%80%93normal_form_and_general_form_of_the_equation_of_a_plane) <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Plane_(geometry))]</sup>
      - `Curve` the trail of a moving point <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Curve)]</sup>
      - `Surface` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Surface_(mathematics))]</sup>
      - `Manifold` generalizes curve and surface <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Manifold)]</sup>
      - Length
      - Area
      - Volume
      - `AffineSpace`, no distance, no angles, no origin point <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Affine_space)]</sup>
      - `Incidence` a heterogeneous relation that expresses objects such as points, lines, planes, etc. colliding or lying on top of on another or passing through one another. <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Incidence_(geometry))]</sup>
    - Synthetic- <sup>[[Wikipedia]()]</sup>
    - Analytic- <sup>[[Wikipedia]()]</sup>
    - Algebraic- <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Algebraic_geometry)]</sup>
      - `Lemniscate` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Lemniscate)]</sup>
    - Differential- "uses the techniques of differential calculus, integral calculus, linear algebra and multilinear algebra" <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Differential_geometry)]</sup>
      - `DifferentiableCurve` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Differentiable_curve)]</sup>
  - Non-Euclidean- <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Non-Euclidean_geometry)]</sup>
    - Relaxing metric
      - Kinematic- <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Non-Euclidean_geometry#Kinematic_geometries)]</sup>
    - Relaxing affinity
      - Hyperbolic- <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Hyperbolic_geometry)]</sup>
      - Elliptic- <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Elliptic_geometry)]</sup>
- Topology <sup>[[Wikipedia]()]</sup>
  - =
    - `Topology` $\tau$ <sup>[[Wikipedia]()]</sup>
    - Space
      - `TopologicalSpace` $(X, \tau)$ <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Topological_space)]</sup>
      - Compactness <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Compact_space)]</sup>
      - Metrizability <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Metrizable_space)]</sup>
      - `StoneSpace` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Stone_space)]</sup>
      - Total disconnection <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Totally_disconnected_space)]</sup>
    - `Surface` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Surface_(topology))]</sup>
      - Orientability <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Orientability)]</sup>
  - Algebraic- "uses tools from abstract algebra" <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Algebraic_topology)]</sup>
- Calculus <sup>[[Wikipedia]()]</sup>
  - Differential- <sup>[[Wikipedia]()]</sup>
  - Integral- <sup>[[Wikipedia]()]</sup>
- Analysis <sup>[[Wikipedia]()]</sup>
  - Real- <sup>[[Wikipedia]()]</sup>
  - Complex- <sup>[[Wikipedia]()]</sup>
    - `RiemannSurface` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Riemann_surface)]</sup>
  - Functional- <sup>[[Wikipedia]()]</sup>
  - Nonstandard- <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Nonstandard_analysis)]</sup>
    - `Hyperreal` "A hyperreal $x$ and its [standard part](https://en.wikipedia.org/wiki/Standard_part_function) $st(x)$ are [equal up to](https://en.wikipedia.org/wiki/Up_to) an infinitesimal difference" <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Hyperreal_number)]</sup>
    - `Hyperinteger` <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Hyperinteger)]</sup>
    - `Infinity` $\omega$ <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Infinity)]</sup>
    - `Infinitesimal` $\varepsilon$ <sup>[[Wikipedia](https://en.wikipedia.org/wiki/Infinitesimal)]</sup>
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
