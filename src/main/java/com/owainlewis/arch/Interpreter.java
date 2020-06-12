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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

// TODO the level of reflection here is painful and unnecessary

public final class Interpreter {

  private Stack<Expression> runtimeStack = new Stack<>();

  private Map<String, Function<Stack<Expression>, Stack<Expression>>> env = new HashMap<>();

  public Interpreter() {
      this.env.put("swap", Operations.swap);
      this.env.put("debug", Operations.debug);
      this.env.put("+", Operations.binOpPlus);
  }

  public void interpret(List<Statement> statements) {

    Function<Stack<Expression>, Stack<Expression>> f = (Stack<Expression> s)-> {
        Expression e1 = s.pop();
        System.out.println(e1);
        return s;
    };

    for (Statement statement : statements) {
      if (isExpression(statement)) {
        Statement.ExpressionStmt stmt = (Statement.ExpressionStmt) statement;
        Expression e = stmt.getExpression();
        if (e.getType() == Expression.Type.Word) {
            // Do a lookup dispatch
            Expression.Literal expr = (Expression.Literal) e;
            String word = (String) expr.getValue();
            if (env.containsKey(word)) {
                env.get(word).apply(runtimeStack, statements);
            } else {
                // This is an error I think?
                throw new IllegalArgumentException("Word called before bound");
            }

        } else {
          runtimeStack.push(e);
        }
      }
    }
  }

  private boolean isLetStatement(Statement statement) {
    return (statement instanceof Statement.LetStmt);
  }

  private boolean isExpression(Statement statement) {
    return (statement instanceof Statement.ExpressionStmt);
  }
}
