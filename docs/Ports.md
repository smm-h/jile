# Ports and Processors

## Rationale

Code objects are extremely general and may produce almost anything when processed. Given how Java is statically typed, we cannot ask a code for its processed objects in a type-safe manner. Instead, we ask for data structures that [securely](#Security) hold these objects.

## Ports

Ports are repositories that only qualified personnel (their processors) may put objects in or take objects from.

```java
class Port<T> {
    void put(Code code, T thing);
    T take(Code code);
}
```

The method `put` overwrites the stored thing, and `take` removes and returns it.

## Processors

Nilex revolves around multi-processors, previously known as languages.

```java
class Multiprocessor implements Processor {
    Set<Port> hasReadAccessTo;
    List<Processor> processors;
}
```

A multi-processor is comprised of other multi-processors and single-processors.

```java
class SingleProcessor implements Processor {
    Set<Port> requiresReadAccessTo;
    Set<Port> owns;
}
```

When a single-processor owns a port, it has write access to it.

Both single and multi-processors are derived from processors.

```java
interface Processor {
    void process(Code code);
}
```

When given a code, processors read from, process, and and write to the ports the have access to.

If a multi-processor has all the ports a single-processer requires access to, it may be extended with it. When this happens, the multi-processor gains read access to all of the ports the single-processor owns.

## Security

Each well-defined port subclass is a private class that implements the read-only port interface.

```java
interface ReadOnlyPort<T> {
    T takeACopy(Code code)
}
```

Then when an instance of it is returned to the wild, it can only be used to obtain copies of the processed objects and not alter or remove the original.

## Examples

- `Tokenizer`
  - Writes `Code.syntax`
  - Writes `Tokenizer.tokenized`
- `Organizer`
  - Reads `Tokenizer.tokenized`
  - Writes `Grouper.grouped`
- `TokenizerMaker`
  - Reads `Tokenizer.tokenized`
  - Writes `Tokenizer`
- `OrganizerMaker`
  - Reads `Tokenizer.tokenized`
  - Writes `Organizer`
- `TreeLanguage`
  - Reads `Tokenizer.tokenized`
  - Writes `LinkedTree<String>`
- `TurtleGraphicsLanguage`
  - Reads `Grouper.grouped`
  - Writes `TurtleGraphics`
- `PercentDecoder`
  - Writes `String`
- `Highlighter`
  - Reads `Tokenizer.Syntax`
  - Writes `CodeView`
