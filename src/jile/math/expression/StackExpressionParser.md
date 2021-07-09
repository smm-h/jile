# Infix to Postfix using a stack

1. First Start scanning the expression from left to right
2. If the scanned character is an operand, output it, i.e. print it
3. Else
    3.1. If the precedence of the scanned operator is higher than the precedence of the operator in the stack(or stack is empty or has'(‘), then push operator in the stack
    3.2. Else, Pop all the operators, that have greater or equal precedence than the scanned operator. Once you pop them push this scanned operator. (If we see a parenthesis while popping then stop and push scanned operator in the stack)
4. If the scanned character is an ‘(‘, push it to the stack.
5. If the scanned character is an ‘)’, pop the stack and output it until a ‘(‘ is encountered, and discard both the parenthesis.
6. Now, we should repeat the steps 2 – 6 until the whole infix i.e. whole characters are scanned.
7. Print output
8. Do the pop and output (print) until stack is not empty
