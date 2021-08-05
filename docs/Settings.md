# Make your own settings sub-language

## Outline

1. Create a new class in a Java source code file and extend the `jile.lingu.SettingsLanguage` class.
2. (Optional) Make sure it `implements Singleton` and it is loaded in `Languages.singleton`.
3. Make a `super` call in its sole constructor and pass its name and installer, which usually is `Tokenizer.singleton()`.
4. Implement the supertype method `makeBlank`, as such:

```java
@Override
public ExportableSettings makeBlank(String type) {
    switch (type) {
    case "...":
        return new ...;
    default:
        return null;
    }
}
```

## Making settings types

For each setting type you want to make, follow these steps:

1. Choose a name for it that is unique inside the language and made up entirely of lower-case letters and underscores.
2. Make a dynamic inner class with that name (in _PascalCase_) that `extends ExportableSettings`.
3. Add a `case` _name_ `:` to the main `switch` in `makeBlank`, that returns a new instance of that class.
4. Make a `super` call in the sole constructor of that class and pass an anonymous `AbsoluteSettingsType` that implements `beDefined` as such:

```java
super(new AbsoluteSettingsType() {
    @Override
    public void beDefined() {
        add("property-name");
        add("another-property", "'default value'");
        add("numeric-property", "0");
        add("vector-property", "[]");
        // ...
    }
}
```

## Making properties for a type

For each property you want to make for a type, follow these steps:

1. Choose a name for it that is unique inside the type and made up entirely of lower-case letters and dashes.
2. Declare its header in `beDefined`, as seen in the previous section.
3. Declare its actual Java property with the same name (in _camelCase_) in the class of that type, and its initializer in the sole constructor of that class, as such:

```java
private final SomeType propertyName;
private final String anotherProperty;
private final int numericProperty;
private final List<SomeOtherType> vectorProperty;
// ...

@Override
public void beSetUp(AbsoluteSettings blank) {
    this.propertyName = blank
}
```

## Absolution

Settings written in a nested manner inside curly braces are dubbed `RelativeSettings` and must undergo two phases of absolution to become usable by your program:

1. Generic absolution
2. Specific absolution

### Generic Absolution

Your extension of `SettingsLanguage` implements `findType` which when provided with the `String` type of a `RelativeSettings` instance, returns an instance of `AbsoluteSettingsType`, which is used to automatically turn our relative settings into absolute settings. The only difference between these two is that absolute settings have absolute values and names. But they are still members of the `AbsoluteSettings` class which is not fun to work with.

### Specific Absolution

Your extension of SettingsLanguage also implements `make` which takes both the `String` type, and an instance of `AbsoluteSettings`, and returns an instance of a subclass of `ExportableSettings`, which are fun to work with.
