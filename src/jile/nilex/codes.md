# Codes

[:back:](readme.md)

## `Code`

[:scroll:](../lang/Code.java)

A code is essentially a file written in a certain [language](#language). A code object internally uses the [resource](#resource) class instead of directly working with files.

## `Encodeable`

[:scroll:](../lang/Encodeable.java)

Anything that can be losslessly converted into a [code](#code) object and then stored as a file.

## `Resource`

[:scroll:](../io/Resource.java.java)

A resource is a thin wrapper that unifies working with files on the disk, inside JARs, and virtual files that exist only in RAM.
