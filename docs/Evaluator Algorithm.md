# Algorithm

```m
[a+b*c-d+e/f]
[           ]
```

- G $\leftarrow$ `b*c`

```m
[a+   -d+e/f]
[  GGG      ]
```

- H $\leftarrow$ `e/f`

```m
[a+   -d+   ]
[  GGG   HHH]
```

- I $\leftarrow$ `a+`G

```m
[     -d+   ]
[IIIII   HHH]
```

- J $\leftarrow$ I`-d`

```m
[       +   ]
[JJJJJJJ HHH]
```

- K $\leftarrow$ J`+`H

```m
[           ]
[KKKKKKKKKKK]
```
