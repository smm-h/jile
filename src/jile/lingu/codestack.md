# Codestack

[:back:](readme.md)

## `Codestack`

[`Codestack.java`](Codestack.java) :arrow_forward:

Codestack is the CLI of the lingu project. As its name suggests, it lets the user interact with a stack on which [codes](codes.md#code) and other related objects can be pushed. If the object on top of the stack is a [view](../vis/viewing.md#view), a minimalistic GUI called the [viewer](../vis/viewing.md#viewer) can be invoked to view it.

Textual commands can be used to push objects of different types on stack. Some commands operate on the top object without pushing anything. Some commands will require string arguments. Make sure to enclose them in only double quotations or not at all; single quotations do not work.

The available commands vary depending on the type of the object on top of the stack:

| Top object                                     | Command                    | Description                                                                                                       | Effect |
| ---------------------------------------------- | -------------------------- | ----------------------------------------------------------------------------------------------------------------- | ------ |
| Anything                                       | `load "filename.ext"`      | Reads a file and pushes it as a `Code` object                                                                     | +      |
| Anything                                       | `load-as "contents" "ext"` | Pushes the contents as `Code` object in the specified language                                                    | +      |
| Anything                                       | `lang "ext"`               | Pushes the `Language` object associated with a given extension                                                    | +      |
| [Code](codes.md#code)                          | `save`                     | Saves the top `Code` object file in-place                                                                         | =      |
| Code                                           | `save-as "filename.ext"`   | Saves the top `Code` object as another file                                                                       | =      |
| Code                                           | `make`                     | Uses the main maker of the language of the top `Code` object to make something, and then pushes that on the stack | +      |
| Code                                           | `highlight`                | Pushes syntax highlighting as a `CodeView` object                                                                 | +      |
| Code                                           | `get-syntax-tree`          | Pushes syntax tree as a `TreeView` object (from the `Grouper.grouped` [port](processes.md#port)))                 | +      |
| Code                                           | `get-tree-view`            | Pushes the tree representation of as a `TreeView` object (from the `TreeView.port` port)                          | +      |
| [Visualizable](../vis/viewing.md#Visualizable) | `visualize`                | Visualizes the top `Visualizable` object and pushes its `View` object                                             | +      |
| [View](../vis/viewing.md#View)                 | `show`                     | Displays the top `View` object in the [viewer](#viewer)                                                           | =      |
| [Encodeable](codes.md#encodeable)              | `encode`                   | Encodes the top `Encodeable` object into a `Code` object and pushes it                                            | +      |
| Anything                                       | `pop`                      | Pops the top object                                                                                               | -      |
| Anything                                       | `help`                     | Shows help                                                                                                        | =      |
| Anything                                       | `exit`                     | Exits the CLI                                                                                                     | =      |
