# `jile`

[:back:](../../readme.md)

## Background

I used to be estranged with [Java]() until [a freelancing project]() forced me to learn it, and except for [a few minor details](), come to love its [object-oriented specification]() and [various conventions](). From then on, Jile has become the rock on which I have built everything else. The word "Jile" is a portmanteau of the words Java and [Nile]().

The project that started Jile was what is known today as the **Jilic Linguistics**. It was initially known as **NiLEX**, and had [a long history]() before Jile, that spanned many years and many languages. The second project was **Jilic Mathematics**, that [also started out as a freelancing project](). Any other well-defined Java project that I take on will eventually become a part of Jile.

## Goals

- **Purity**: relying as little as possible on the standard and third-party libraries
  - Rationale: so it can one day be ported to [Android]()
- **Interrelation**: having packages "play nice" with each other, especially with `common`. Reducing duplicate semantics and keeping the code as [DRY]() as possible
  - Rationale: maintainibility

## Packages

- [`lingu`](lingu/readme.md) **Jilic Linguistics**
- [`math`](math/readme.md) **Jilic Mathematics**
- [`cs`](cs/readme.md) **Jilic Computer Science**
- [`vis`](vis/readme.md) Visualizations
- [`common`](common/readme.md) Common utilities

And coming soon,

- `gm`, [pygame]()-like wrapper over [SDL](), mimicing **[GML]() semantics** to which I was once addicted
- `therm` deals with energy, mass and discrete time in the context of **Jilic Thermodynamics**; heavily uses Jilic mathematics

## Appendix

[See what emojis might mean](emojis.md)
