# Terminology of Nilex

To get links to work, first replace all `(\W+)~(\W+)` with `[$1](#$2)`, and then replace all `(\W+)~` with `[$1](#$1)`.

Legend:

- =: synonymous
- ≠: antynymous
- ∈: is a part of
- ∉: is not a part of
- ∋: contains
- ∌: does not contain

## **synthesizer**

Any object that can decode~ a code~ and return a 

## **language**

- = decoder~
- = `Language

A class that extends `Language`

Essence: being able to @analyze and @synthesize a @code

## **decoder**

- = language~

A class that extends `Language`

## **code**


## **synthesize**


## **decode**

- ≠ ~encode

The process of turning a code~ into a Java object which may or may not be encodeable~. Even if it is, encoding it may not result in the original code. In other words: decoding is not (at least trivially) reversible; but it is deterministic.

## **encode**

- ≠ ~decode

The opposite of decoding~decode.

The process of turning an encodeable~ object into a code~.

## **analyze**

The first half of decoding~decode.

Taking a code~ and, first tokenizing~tokenize it, and then enclosing~enclose it.

## **tokenize**

The first half of analysis~analyize.

## **enclose**

The second half of analysis~analyize.
