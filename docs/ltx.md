# Literate Expression

## Overview

A Literate Expression is a plain text file that, in a [literate](https://en.wikipedia.org/wiki/Literate_programming) way and using "almost natural" wording, "defines what an [expression](#syntax) would [mean](#semantics) in a given [context](#context)". These files have the file extension `*.ltx`, which is pronounced as _lit-ex_.

## Syntax

A litex file is comprised of lines, the first of which is dubbed "the header" and the rest "the body". The header has the following form:

```ltx
word1-word2-$input1-word3-$input2 : $output1-word4-$output2
```

and the body is written in [s-expressions](https://en.wikipedia.org/wiki/S-expression).

The header may end in

### Puncuation

LitEx has no keywords or reserved words; instead it has only a handful of puncuation symbols.

| Symbol | Usage                                        |
| ------ | -------------------------------------------- |
| `:`    | Separates input and output in the header     |
| `,`    | Ends an invokations inside a sentence.       |
| `.`    | Ends a sentence inside a paragraph.          |
| `;`    | Ends a sentence without popping the context. |
| `\n`   | Ends a paragraph.                            |
| `?`    |                                              |
| `$`    | Substitutes a word in the header             |

## Semantics

## Context

## Constructs

- **definition**: each file defines one or more expressions
- **invoking**: each expression body is comprised of words that may constitue other expressions
- **recursion**: an expression may invoke itself

## Predefined expressions

```txt
if-$boolean-then-$expression[-else-$expression]
iterate-over-$iterable-and-$expression
```

```txt
add-($double-or-$int)-to-($double-or-$int)
subtract-($double-or-$int)-from-($double-or-$int)
multiply-($double-or-$int)-by-($double-or-$int)
divide-($double-or-$int)-by-($double-or-$int)
```

## Workflow

1. An executable expression (any expression that has test input specified, or requires no input) is executed.
2. It contains other expressions, or itself. Other expressions also contain more expressions. A special kind of expressions are the `define-[$article]-$word[-in-the-context-of-$context]` expressions that define ideas rather than outputting something.
3. Words are lazily defined as expressions are traversed, until actual machine code is encountered in which case it is executed.

---

1. Write your source files as `*.lpc` files
2. Compile them to Java or Python code
3. Generate equivalent Markdown from them

## Format

The structure of a `*.lpn` file is as follows:

- What domain it belongs to
- What concept it defines
- The definition
  - What it is
  - What it has
  - What it can do
  - What can be done on it
- How it can be transformed into Markdown

The structure of a `*.lpv` file is as follows:

- What domain it belongs to
- What concept it defines
- The definition
  - What it is
  - What it has
  - What it can do
  - What can be done on it
- How it can be transformed into Markdown

## The editor

LitPad lets you work on `*.ltx` files. It is meant to be an intelligent replacement for various notepad apps. It has three goals:

- Be as fast as notepad apps
- Be as concise as natural language
- Be as intelligent as the machine offers
