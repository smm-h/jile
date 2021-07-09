# Uniform Access to Resources

To be able to access all your files in a uniform fashion in your Java project using the `Resource` class, put them in the `src/res` folder of the following recommended project directory hierarchy:

- _project name_
  - `.trash`, contains files you've worked hard but are pointless now but may come in handy in the future.
  - `.vscode`
  - `lib`, contains `*.jar` dependencies
  - `bin`, contains compiled `*.class` files, mirroring `*.java` files in `src`
  - `docs`, contains `*.md` files
  - `jar`, contains archived `*.jar` files
  - `jarbuilder`, contains shell scripts that automate the process of compiling and making JARs.
  - `src`, contains packages and resources
    - `res`, contains your resources that you want to access uniformly
    - _package name_, contains `*.java` files
