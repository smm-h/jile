
# Commands for Codestack

Here are some of the commands available out-of-the-box:

The available commands vary depending on the object on the top of the stack:

- `Code`
  - `save` does not affect the stack
  - `save-as "filename.ext" yes/no` does not affect the stack
  - `decode` pushes `Decoded` which may also be `Encodeable`
  - `highlight` pushes syntax highlighting as `CodeView`, which is a `View`
  - `get-syntax-tree` pushes syntax tree as `TreeView`, which is `Decoded` and `Encodeable` `View`
- `Decoded`
  - `get-source` pushes `Code`
  - `visualize` pushes `View`
- `Encodeable`
  - `encode` pushes `Code`
- `View`
  - `show` does not affect the stack
- Always available
  - `load "filename.ext"` pushes `Code`
  - `load-as "contents" "ext"` pushes `Code`
  - `lang "ext"` pushes `Language` which is both `Decoded` and `Encodeable`
  - `pop` pops one object off the stack
  - `exit` exits the loop
