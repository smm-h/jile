# A Guide to Fabric and Dye

## The problem

User interface objects, such as the instances of the subclasses of the `View` class, like `TreeView` and `CodeView`, and even the `Viewer` singleton itself, heavily rely on drawing various shapes and texts to convey meaning. It is impractical and unmaintainable for the classes to have fields for the exact colors of these elements for different situations such as "dark mode", "being selected", "being hovered over", etc.

## The solution

This article discuess two classes that togather are used to create colors<sup>*</sup> that respond correctly to different situations:

- Fabric: a gray semi-transparent colorless texture.
- Dye: an specific shade of a specific color.

Fabrics alone can be used to make grayscale colors, but dyes alone cannot and should not be used to make color objects<sup>*</sup>.

Both classes are immutable, and all their methods are deterministic.

- *: `java.awt.Color`

## How it works

Fabric objects and dye objects do not hold internal color fields. Instead, fabrics have `make()` methods, with the following signatures, that are used to create situation-specific colors.

- `make()`: grayscale
- `make(boolean contrast)`: grayscale but with extra contrast
- `make(Dye dye)`: imbued
- `make(Dye dye, boolean contrast)`: imbued and with extra contrast

They all return `Color`. Traditional colors are comprised of hue, saturation, brightness, and optionally transparency. Fabrics are solely responsible for the value of _transparency_, and dyes for _hue_. They both are responsible for value of the _saturation_ and _brightness_.

If a dye is naturally dark, it will be light in dark mode, and vice-versa.

## Dark mode flag

Unlike the dye and the contrast flag which are passed to `make` to indicate a situation to react to, the dark mode indicating boolean flag is not passed to `make`, but read from the `darkMode` field of the `Views` singleton. It is recommended that you incorporate this hint into your app. For example it is common practice to bind <kbd>F9</kbd> to call `Views.toggleDarkMode()`.
