# `jile.lingu`

[_Go up one level_](../readme.md)

## Introduction

When you come across a text written in **natural** language, three things happen in this order:

1. You look at it, and your brain is fed a visual stream of it
2. From that stream, you create things **inside** your mind, that mirror it semantically but not syntactically. This step is called understanding or **internalization**.
3. Later on, you use that internalization and other things to create things **outside** your mind. This step is called **externalization**.

The same steps exist within **formal languages** and texts written in them, such as a source code written in a specific programming language: a compiler loads the contents of the source code file into RAM, lexes and parses it to create an IR, and then generates machine code targetted to some ISA. In this scenario the IR is the **internalization**, and the machine code is the **externalization**.

## Jilic Linguistics

**Jilic Linguistics** is the study of [codes](codes.md) written in [Jilic languages](languages.md). It is in these languages that the previously introduced notions of [internalization and externalization]() are formally defined and implemented.

The two most common internalization processes that almost every language have are [tokenization](tokenization.md) and [parsing](grouping.md). Another general process is reading settings from [a language well-suited for formally expressing them](formalized-settings.md).

`lingu` comes with its own CLI, [Codestack](codestack.md), that lets the user push objects, such as codes and things made from them, onto a stack. Codestack can also be used to [preview visual objects on the stack by invoking the Viewer GUI](../vis/readme.md).

- Code
- Language
- Port
- Processor
