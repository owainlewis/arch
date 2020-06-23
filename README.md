# Arch

Arch is a stack based functional programming language based on combinatory logic.

## Prerequisites

- Java 11+
- Gradle

## Getting Started

You can start an interactive REPL session with

```
./gradlew run --console=plain
```

## Language

```ocaml
module base

import symbols ( foo, bar );

let y = 10 ;
let z = 20 ;

private let double :: int -> int = 2 * ;

let bar :: string int -> [string] =
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

## Operations

```
operator

id      :  ->
Identity function, does nothing.
Any program of the form  P id Q  is equivalent to just  P Q.

dup      :   X  ->   X X
Pushes an extra copy of X onto stack.

swap      :   X Y  ->   Y X
Interchanges X and Y on top of the stack.

rollup      :  X Y Z  ->  Z X Y
Moves X and Y up, moves Z down

rolldown      :  X Y Z  ->  Y Z X
Moves Y and Z down, moves X up

rotate      :  X Y Z  ->  Z Y X
Interchanges X and Z

popd      :  Y Z  ->  Z
As if defined by:   popd  ==  [pop] dip

dupd      :  Y Z  ->  Y Y Z
As if defined by:   dupd  ==  [dup] dip

swapd      :  X Y Z  ->  Y X Z
As if defined by:   swapd  ==  [swap] dip

rollupd      :  X Y Z W  ->  Z X Y W
As if defined by:   rollupd  ==  [rollup] dip

rolldownd      :  X Y Z W  ->  Y Z X W
As if defined by:   rolldownd  ==  [rolldown] dip

rotated      :  X Y Z W  ->  Z Y X W
As if defined by:   rotated  ==  [rotate] dip

pop      :   X  ->
Removes X from top of the stack.

choice      :  B T F  ->  X
If B is true, then X = T else X = F.

or      :  X Y  ->  Z
Z is the union of sets X and Y, logical disjunction for truth values.

xor      :  X Y  ->  Z
Z is the symmetric difference of sets X and Y,
logical exclusive disjunction for truth values.

and      :  X Y  ->  Z
Z is the intersection of sets X and Y, logical conjunction for truth values.

not      :  X  ->  Y
Y is the complement of set X, logical negation for truth values.

+      :  M I  ->  N
Numeric N is the result of adding integer I to numeric M.
Also supports float.

-      :  M I  ->  N
Numeric N is the result of subtracting integer I from numeric M.
Also supports float.

*      :  I J  ->  K
Integer K is the product of integers I and J.  Also supports float.

/      :  I J  ->  K
Integer K is the (rounded) ratio of integers I and J.  Also supports float.

rem      :  I J  ->  K
Integer K is the remainder of dividing I by J.  Also supports float.

div      :  I J  ->  K L
Integers K and L are the quotient and remainder of dividing I by J.

sign      :  N1  ->  N2
Integer N2 is the sign (-1 or 0 or +1) of integer N1,
or float N2 is the sign (-1.0 or 0.0 or 1.0) of float N1.

neg      :  I  ->  J
Integer J is the negative of integer I.  Also supports float.

ord      :  C  ->  I
```
