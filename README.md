# Arch

Arch is a stack based functional programming language based on combinatory logic.

```ocaml
module base

import symbols as sym

let y = 10 ;
let z = 20 ;

private let double (int -> int) = 2 * ;

private let foo ([..] [..] -> [..] ) =
  dup swap pop sym.foo
;

let bar (string, int -> string) =
  [ ] [dip] i [swap] push pop rot over2
;

let main = double bar foo read
```
## Type system

TBD

```ocaml
let gcd :: int int -> int [int] =
  { }
;
```

## Variables and functions

You can assign a value to a name using the `let` statement.

```ocaml
let x = 10 ;
```

Given that arch is a stack based language a function is just a series of operations assigned to a name. As such, functions are defined in exactly the same
way as variables are.

```ocaml
let double = 2 * ;
```
