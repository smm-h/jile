# What exactly happens?

- `Nilex.languages` is an empty mapping from exts to `Language`s
- `Nilex.meta` becomes a new `NilexMetaLanguage` with its translator set.
- `SimpleTreeLanguage` creates a new dynamic instance of itself, which:
  - calls its `super()` to call `Nilex.addLanguage` on itself.
    - creates a `Code` object from `nlx/SimpleTree.nlx`
    - pushes this language on the stack of `Nilex.meta`
    - [x] `Code` is `process`ed to:
      - be tokenized.
      - [x] be translated,
        - which associates that language with its ext(s),
        - and defines its definitions.
    - pops it
  - makes its tokenizer, using definitions.
  - makes its translator, using code literal.
- `SimpleTreeLanguage.main` creates a new `Code` containing the contents of a test tree.
- [x] That `Code` object now has to be `process`ed so that it:
  - updates its ext from its filename.
  - loads its String from its Path.
  - gets tokenized.
  - [x] gets translated.

