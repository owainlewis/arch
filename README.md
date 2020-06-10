# Arch

Arch is a stack based functional programming language based on combinatory logic.

```ocaml
module base

import symbols as sym

let y = 10 ;
let z = 20 ;

private let double (X : X) = 2 * ;

private let foo ([A] [B] : [A] B) =
  dup swap pop sym.foo
;

let bar =
  [ ] [dip] i [swap] push pop rot over2
;

let main = double bar foo read
```
## Type system

TBD

```
>> A variable which returns an integer and pushes it onto the stack
let x (int) = 10;

>> A function that expects two integers on the stack and produces a single integer
let y (int, int -> int) = pop inc
```

## Variables and functions

You can assign a value to a name using the `let` statement.

```
let x = 10 ;
```

Given that arch is a stack based language a function is just a series of operations assigned to a name. As such, functions are defined in exactly the same
way as variables are.

```
let double = 2 * ;
```
