/*
 * Copyright © 2020 Owain Lewis <owain@owainlewis.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.owainlewis.arch;

import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class defines all of the primary operations in arch.
 */
public class Operations {

private interface BiOperation extends BiFunction<Stack<Statement>, Stack<Expression>, Stack<Expression>> {}

// Testing the i combinator
    public static BiOperation iCombinator = (Stack<Statement> instructions, Stack<Expression> stack) -> {
        System.out.println("HELLO");
        instructions.forEach(System.out::println);

    System.out.println(instructions.size());
        Expression e1 = stack.pop();
        if (e1.getType().equals(Expression.Type.List)) {
            Expression.ListExpr le = (Expression.ListExpr) e1;
            List<Expression> expressions = le.getExpressions();
            expressions.stream().forEach(e -> instructions.push(new Statement.ExpressionStmt(e1)));
        }

        System.out.println(instructions.size());
        return stack;
    };

    /**
     * Operation is an interface used to type alias the generic stack function
     */
    private interface Operation extends Function<Stack<Expression>, Stack<Expression>> {}
    /**
     * Debug
     *
     * [] -> void
     *
     * <p>Prints the contents of the entire stack printing each item on a new line</p>
     */
    public static Operation debug = (Stack<Expression> s) -> {
        s.forEach(System.out::println);
        return s;
    };

    /**
     * Swap
     *
     * [x:y] -> [y:x]
     *
     */
    public static Operation swap = (Stack<Expression> s) -> {
        Expression e1 = s.pop();
        Expression e2 = s.pop();
        s.push(e1);
        s.push(e2);
        return s;
    };

  public static Operation binOpPlus = (Stack<Expression> s) -> {
        Expression e1 = s.pop();
        Expression e2 = s.pop();

        if (e1.getType().equals(Expression.Type.Integer) && e2.getType().equals(Expression.Type.Integer)) {
             Integer a = (Integer) e1.getValue();
             Integer b = (Integer) e2.getValue();
             s.push(new Expression.Literal(Expression.Type.Integer, a + b));
        }

        // Type error

        return s;
      };

  public static Operation iCombinator =
      (Stack<Expression> s) -> {
        Expression e1 = s.pop();
        if (e1.getType().equals(Expression.Type.List)) {
            Expression.ListExpr le = (Expression.ListExpr) e1;
            List<Expression> expressions = le.getExpressions();



        }
        return s;
      };
}