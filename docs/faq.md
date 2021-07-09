# FAQ

- **Q:** Why is there a `pop` command but no `push` command?
- **A:** Almost all commands except `pop` are in fact `push` commands. For example, when a command like `f` is invoked, it takes the top of the stack as an argument, and pushes its output on the stack. In other words, `f` is `push(f(top))`; unless `f` returns nothing, in which case `f` is `