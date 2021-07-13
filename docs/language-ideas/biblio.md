# biblio

no digits or uppercase letters allowed, except for single letters

## No constructors, only casting

Java's:

```java
class Student {
    String name;
    float grade;
    student(String name, float grade) {
        this.name = name;
        this.grade = grade;
    }
}

Student student = new Student("some dude", 20.0f)
```

would be:

```lisp
(define "student"
    (has "name" string)
    (has "grade" float)
)

(cast student
    (map
        (string "some dude")
        (float 20.0)
    )
)
```

## Cardinality

Cardinality is a form of quiddity: "what is that? that is two apples."

```lisp
(cast set)

(p (the set))
```

- any natural number in written form, e.g. `zero` or `one`
- articles, e.g. `a-(`...`)`, `an-(`...`)` and `the-(`...`)`
- `many-(`...`)` or `any-number-of-(`...`)`
- exclusive double-side limit: `between-(`...`)-and-(`...`)`
- inclusive double-side limit: `between-(`...`)-and-(`...`)`
- exclusive single-side limit
  - `more-than-(`...`)`
  - `less-than-(`...`)`
- inclusive single-side limit
  - `(`...`)-or-more`
  - `(`...`)-or-less`
- `infinity`
- sign indicator
  - `negative-(`...`)`
  - `positive-(`...`)`

data = abstraction + storage

cardinality = how many of them are there?
quiddity = what are they?
storage = how are they stored?

- `[how many][what][stored how]`
