# Make your own Language Installer (in 3 steps)

## 1. Define the class

Choose a name for it, like `MyMetaLanguage`, and create its Java class:

```java
public class MyMetaLanguage extends MetaLanguage
```

Be sure to import `jile.nilex.MetaLanguage`.

## 2. Set-up its LANGPATH and test files

Define its sole constructor:

```java
public MyMetaLanguage() {
    super("My Meta-Language", "myml", "myml");
    
    var ci = Tokenizer.singleton().new Installation();
    ci.define(...);
    // ...
    setInstallation(ci);
}
```

and install it manually using another installer, like `Tokenizer.singleton()`.

Then make a folder in `res` called `myml` and populate it with files that have the `myml` extension and describe languages that can be installed with your installer.

Finally, implement the `getResource` method. The following is an acceptable default implementation for it:

```java
@Override
public Resource getResource(String name) throws NullPointerException {
    return Objects.requireNonNull(Resource.of(langPath + "/" + languageName + "." + primaryExt));
}
```

It should never return `null`, or throw anything other than `NullPointerException`.

## 3. Teach it how to install

Every installer must be able to `install`, but since that has been taken care of in the super-class, we need only implement the heart of it: decoding the grammar code.

```java
@Override
public Installation decode(Code code) {

    // first get the `Language` object whose installation we are currently setting
    Language language = getCurrentLanguage();

    // then get its installation so that we can perform modifications on it
    Installation ci = (Installation) language.getInstallation();

    // `ci` is short for (c)urrent language (i)nstallation
    
    // if we are installing and not importing,
    if (ci == null) {

        // assign it a new modifiable installation
        ci = new Installation();
        language.setInstallation(ci);
    }

    // perform modification methods on `ci` based on `code`
}
```

This `decode` method will be called from two different places:

1. `MetaLanguage.install`, `ci` is `null`
2. `MetaLanguage.loadImport`, `ci` is not `null`

But first, you must extend the `Installation` to have your own modifiable installation:

```java
public class Installation extends MetaLanguage.Installation
```

Be sure to define a bunch of modification methods for it.

And in it, `Processing`:

```java
public class Processing extends MetaLanguage.Installation.Processing
```

which is merely a wrapper for the tokens which will be processed by the installation.
