# Processing

[:back:](readme.md)

## `Process`

[:scroll:](../lang/Code.java#Process)

Any processing of code object $C$ involves three steps:

1. Reading input data of type $T_{I_{i}}$ using $C$ as the key from $n$ [ports](#port) where $0 \leq i < n$,
2. Using that $T_{I_{i}}$ data to produce output data of types $T_{O_{j}}$,
3. Writing $T_{O_{j}}$ data on $m$ ports using $C$ as the key where $0 \leq j < m$, so other processes can use it.

Any object that does a process is known as a [processor](#processor).

## `Processor`

[:scroll:](../lang/Processor.java)

A processor is meant to take a [code](#code) object, read its associated data from some [port](#port)s, instantly process them somehow, and then write to some other ports. Processor can also be executed in chains using [multi-processor](#multiprocessor)s.

## `Multiprocessor`

[:scroll:](../lang/Multiprocessor.java)

A multi-processor is a [processor](#processor) whose process involves the consecutive processes of multiple other processors. It was created to allow a [language](#language) to be conveniently extended using extra processors.

## `Port`

[:scroll:](../lang/Port.java)

A generic port of type $T$ is essentially a mapping from [code](#code) objects to objects of type $T$. It is the duty of [processors](#processor) to read data from and write data to these ports, using the code object they are processing.

## Error handling

Given how `Process` is an inner class of the [code class](codes.md#code), it is safe to say every process has a code owner. Since processes are meant to be happening seamlessly and constantly in the background, they have to make as little noise as possible. Therefore unlike other portion of Java code, anything that happens as part of a "process" must not throw any exceptions, but silence them and instead "[issue mishaps](mishaps.md)".
