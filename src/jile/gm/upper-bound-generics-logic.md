The logic behind compiler warning for

# Upper-bound Generics

```java
abstract class B {
    public B(Collection<? extends B> c) {
        c.add(this);
    }
}

class C extends B {...}
class D extends B {...}
```

`Collection<? extends B> c` can refer to any type of `List` that stores any type of `B`. So it is possible that:

```java
Collection<? extends B> c1 = new Collection<C>();
Collection<? extends B> c2 = new Collection<D>();
```

If Java would allow you to add `C` to `c1` it would also have to let you add `C` to `c2` (reference type is the same) which is not suppose to happen since `c2` should store only `D`s.

Inspired by: <https://stackoverflow.com/a/15775562/9115712>
