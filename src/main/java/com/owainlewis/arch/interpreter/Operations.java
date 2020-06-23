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
package com.owainlewis.arch.interpreter;

import com.owainlewis.arch.Expression;
import com.owainlewis.arch.Statement;

import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;

/**
 * This class defines the initial dictionary words for Arch.
 *
 */
public final class Operations {

  public interface BiOperation
      extends BiFunction<Stack<Statement>, Stack<Expression>, Stack<Expression>> {};

  private static void ensureN(int n, Stack<Expression> stack) {
      if (stack.size() < n) {
          throw new IllegalStateException("Expected " + n + " values on the stack but got " + stack.size());
      }
  }

    /**
     * Swap two values on the stack
     *
     * [a b] swap = [b a]
     */
  public static BiOperation swap = (Stack<Statement> instructions, Stack<Expression> stack) -> {
      ensureN(2, stack);

      Expression e1 = stack.pop();
      Expression e2 = stack.pop();

      stack.push(e1);
   

  public static BiOperation iCombinator =
      (Stack<Statement> instructions, Stack<Expression> stack) -> {
        Expression e1 = stack.pop();
        if (e1.getType().equals(Expression.Type.List)) {
          Expression.ListExpr le = (Expression.ListExpr) e1;
          List<Expression> expressions = le.getExpressions();
          expressions.forEach(e -> instructions.push(new Statement.ExpressionStmt(e1)));
        } else {
          throw new RuntimeException("Type fail");
        }

        return stack;
      };

  public static BiOperation debug =
      (Stack<Statement> instructions, Stack<Expression> stack) -> {
        System.out.print("[ ");
        stack.forEach(e -> System.out.print(e.prettyPrint() + " "));
        System.out.print("]");
        System.out.println("");
        return stack;
      };
}
