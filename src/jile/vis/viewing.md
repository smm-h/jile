# Viewing

[_Go up one level_](readme.md)

## `Viewer`

[`Viewer.java`](Viewer.java)

## `Visualizable`

[`Visualizable.java`](Visualizable.java)

Anything that can be visualized to produce a [view](#view) object.

## `View`

[`View.java`](View.java) :arrow_forward:

Anything that can be viewed by the [viewer](#viewer). A view object is exactly in one mode at a given time:

1. `STILL`
2. `INTERACTIVE`
3. `EDITING`

And these modes differ in what kind of changes they allow to their data:

- Impermenant change
  - Internal movement
  - Selection
  - Colorization
  - Hints
  - Visible
- Permenant change
  - Core data mutation
  - Also known as "state"
  - Invisible

| Mode          | Impermenant change | Permenant change |
| ------------- | ------------------ | ---------------- |
| `STILL`       | No                 | No               |
| `INTERACTIVE` | Yes                | No               |
| `EDITING`     | Yes                | Yes              |
