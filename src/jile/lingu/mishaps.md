# Mishaps

[_Go up one level_](readme.md)

## `Mishap`

[`Mishap.java`](../lang/Mishap.java)

A mishap is an error that happened during a [process](processing.md#process), and was silenced to not disturb the program. A mishap may or may not be "fatal". When a fatal mishap occurs during a process, the other processes that would have followed it will not be executed. Not only each mishap belongs to a [code](codes.md#code) object, but it specificly stores which [individual token](tokenization.md#individualtoken) it originated from.

## All mishap subclasses

- `LinterMishap` (defined in [Linter](Linter.java))
  - `UnexpectedToken`
- `GrouperMishap` (defined in [Grouper](Grouper.java))
  - `Unbalanced`
  - `NoGroupTypeDefined`
  - `NoRootGroupDefined`
  - `TwoDifferentSeparators`
  - `MultitudeOpaqueButSeparated`
  - `MultitudeNotOpaqueButIgnored`
- `SettingsFormalizerMishap` (defined in [SettingsFormalizer](SettingsFormalizer.java))
  - `BothPrefixAndSuffix`
  - `Unbalanced`
  - `InvalidLength`
  - `InvalidValue`
- `SinglePassParserMishap` (defined in [SinglePassParser](SinglePassParser.java))
  - `CannotEnterNullGroup`
  - `UnexpectedEndOfBlock`
  - `IdentifierNotResolved`
- `TokenizerMishap` (defined in [Tokenizer](Tokenizer.java))
  - `UnknownCharacter`
  - `UnknownKept`
- `LindenmayerMishap` (defined in [TurtleGraphicsLanguage](TurtleGraphicsLanguage.java))
  - `RuleNotFound`
