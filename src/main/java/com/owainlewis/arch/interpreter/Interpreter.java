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
import com.owainlewis.arch.Operations;
import com.owainlewis.arch.Statement;

import java.util.*;
import java.util.function.BiFunction;

// TODO the level of reflection here is painful and unnecessary

public final class Interpreter {

  private Stack<Statement> instructions = new Stack<>();
  private Stack<Expression> runtimeStack = new Stack<>();

  // TODO move this to the constructor and inject
  private Map<String, BiFunction<Stack<Statement>, Stack<Expression>, Stack<Expression>>> dictionary = new HashMap<>();

  public Interpreter() {
      this.dictionary.put("test", Operations.iCombinator);
//      this.dictionary.put("debug", Operations.debug);
//      this.dictionary.put("+", Operations.binOpPlus);
//      this.dictionary.put("i", Operations.iCombinator);
  }

  public void interpret(List<Statement> statements) {
      // Push instructions into our instruction stack
      new LinkedList<>(statements)
              .descendingIterator()
              .forEachRemaining(instructions::push);
    while(!instructions.isEmpty()) {
      Statement statement = instructions.pop();
      if (isExpression(statement)) {
        Statement.ExpressionStmt stmt = (Statement.ExpressionStmt) statement;
        Expression e = stmt.getExpression();
        if (e.getType() == Expression.Type.Word) {
            // Do a lookup dispatch
            Expression.Literal expr = (Expression.Literal) e;
            String word = (String) expr.getValue();
            if (dictionary.containsKey(word)) {
                dictionary.get(word).apply(instructions, runtimeStack);
            } else {
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
