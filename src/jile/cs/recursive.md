# Solving recursive equations

- $T(1) := 1$
- $T(n) := {1\over n}\sum_{i=1}^{n-1} T(i) + 3n$

1. ${O(\log n)}$
2. ${O(n)}$
3. ${O(n\log n)}$
4. ${O(n^2)}$

- $T(1) = 1$
- $T(2) = {1\over 2} (T(1)) + 6 = 6{1\over 2}$
- $T(3) = {1\over 3} (T(1) + T(2)) + 9 = 11{1\over 2}$
- $T(4) = {1\over 4} (T(1) + T(2) + T(3)) + 12 = 16{3\over 4}$
- $T(5) = {1\over 5} (T(1) + T(2) + T(3) + T(4)) + 15 = 22{3\over 20}$

---

## Difference

- $\Delta(k) := T(k+1) - T(k)$
- $= ({1\over (k+1)}\sum_{i=1}^{(k+1)-1} T(i) + 3(k+1)) - ({1\over (k)}\sum_{i=1}^{(k)-1} T(i) + 3(k))$

For example:

- $\Delta(1) = 6{1\over 2} - 1 = 5{1\over 2}$
- $\Delta(2) = 11{1\over 2} - 6{1\over 2} = 5$
- $\Delta(3) = 16{3\over 4} - 11{1\over 2} = 5{1\over 4}$
- $\Delta(4) = 22{3\over 20} - 16{3\over 4} = 5{2\over 5}$
