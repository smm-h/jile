# Codes

[:back:](readme.md)

## `Code`

[:scroll:](../lang/Code.java)

A code is essentially any plan text file written in a certain [language](languages.md).

Instead of directly working with files, a code object internally uses the [resource](#resource) class

## `Encodeable`

[:scroll:](../lang/Encodeable.java)

Anything that can be losslessly converted into a [code](#code) object and then stored as a file.

## `Resource`

[:scroll:](../io/Resource.java.java)

A resource is a thin wrapper that unifies working with files on the disk, inside JARs, and virtual files that exist only in RAM.
