# Logic

superintuitionistic logic -is- propositional logic

superintuitionistic logic -is- intuitionistic logic

classical logic is superintuitionistic logic

classical logic is consistent

intermediate logic is consistent superintuitionistic logic (the logics are intermediate between intuitionistic logic and classical logic)

## Symbol

A symbol is syntax which when interpreted, can deterministically admit a single semantic output from a finite sequence of input symbols.

A constant is a symbol that requires zero input. Example include $5$, $\pi$, $Ø$, $⊥$, etc.

An operator is a symbol that usually requires one, two, or three input symbols, each of which are then called its operands. Examples include $a+b$, $\neg a$, $\Sigma _b ^c a$, $a \over b$, $a!$, etc.

A relation is a symbol that requires at least two input symbols, and has an output of either true or false. Examples include $a=a$, $13|143$, $13>5$, etc.

## Explanation

A **teleological** explanation for something is a function of its
purpose rather than its cause.

## Theory

systematic, comprehensive attempts to solve problems

a self-consistent body of definitions, axioms, theorems, examples, and so on

theory = a model of an axiomatic system

body of knowledge = known axioms and definitions

Peano arithmetic relies on ZFC set theory

## Expression

aka formula, sentence, word

A finite tree of symbols.

## Interpretation

An expression is purely syntactic and has no meaning; until it is interpreted. Interpretation is the meaning assigned to symbols of an expression. An interpretation may also determine the truth value of a sentence.

## Model

A model of a sentence or theory, is an interpretation of it that assigns "true" to it.

## Consequence

- $\therefore$ "therefore"
- $\vdash$ "proves", "entails", "yields", "satisfies"
- $\vDash$ $\models$ "models"

The two techniques for expressing consequence are proofs and models.

## Proof theory: study of syntactic consequence

- $A$ is a formula
- $\Gamma$ is a set of formulas
- $\mathcal {S}$ is a formal axiomatic system

$A$ is a syntactic consequnence of $\Gamma$ within $\mathcal {S}$ if there is a formal proof of $A$ from $\Gamma$ in $\mathcal {S}$.

$$
\Gamma \vdash _{\mathcal {S}} A
$$

## Model theory: study of semantic consequences

- $A$ is a formula
- $\Gamma$ is a set of formulas
- $\mathcal {S}$ is a formal axiomatic system

$A$ is a semantic consequnence of $\Gamma$ within $\mathcal {S}$ iff there is no model $\mathcal {I}$ in which all members of $\Gamma$ are true and $A$ is false. Or, in other words, the set of the interpretations that make all members of \Gamma  true is a subset of the set of the interpretations that make A true.

$$
\Gamma \models _{\mathcal {S}} A
$$
