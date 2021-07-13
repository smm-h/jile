
since the definition of verbatims use that of streaks, all incoming verbatim definition requrests are enqueued and defined after everything else.

| -                   | -             | -                   | -                  |
| ------------------- | ------------- | ------------------- | ------------------ |
| WHITESPACE          | Streak        | whitespace          | " \t\r\n\f"        |
| IDENTIFIER          | Streak        | identifier          | "[0-9][A-Z][a-z]_" |
| LETTERS             | Streak        | letters             | "[A-Z][a-z]"       |
| DIGITS              | Streak        | digits              | "[0-9]"            |
| HEX_DIGITS          | Streak        | hex_digits          | "[0-9]ABCDEF"      |
| PARENTHESIS         | Enclosure     | parenthesis         | "(", ")"           |
| SQUARE_BRACKETS     | Enclosure     | square_brackets     | "[", "]"           |
| CURLY_BRACES        | Enclosure     | curly_braces        | "{", "}"           |
| ARROWS              | Enclosure     | arrows              | "<", ">"           |
| BEGIN_END           | Enclosure     | begin_end           | "begin", "end"     |
| SINGLE_QUOTATIONS   | DarkEnclosure | single_quotations   | "'", "'"           |
| DOUBLE_QUOTATIONS   | DarkEnclosure | double_quotations   | "\"", "\""         |
| SINGLE_LINE_COMMENT | DarkEnclosure | single_line_comment | "//", "\n"         |
| MULTI_LINE_COMMENT  | DarkEnclosure | multi_line_comment  | "/*", "*/"         |