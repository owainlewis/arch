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

import java.util.*;
import java.util.function.BiFunction;

public final class Interpreter {

  private Stack<Statement> instructions = new Stack<>();

  private Stack<Expression> stack = new Stack<>();

  /** Internal dictionary * */
  private Map<String, BiFunction<Stack<Statement>, Stack<Expression>, Stack<Expression>>>
      dictionary = new HashMap<>();

  /** User dictionary * */
  private Map<String, List<Expression>> userDictionary = new HashMap<>();

  public Interpreter() {
    this.dictionary =
        Map.of(
            "debug", Operations.debug,
                "swap", Operations.swap,
            "i", Operations.iCombinator);
  }

  public void apply(Expression e) {
    if (e.getType() == Expression.Type.Word) {
      Expression.Literal expr = (Expression.Literal) e;
      String word = (String) expr.getValue();
      if (dictionary.containsKey(word)) {
        dictionary.get(word).apply(instructions, stack);
      } else if (userDictionary.containsKey(word)) {
        for (Expression ex : userDictionary.get(word)) {
          apply(ex);
        }
      } else {
        throw new IllegalArgumentException("Word called before assignment " + word);
      }
    } else {
      stack.push(e);
    }
  }

  public void interpret(List<Statement> instructions) {
    this.setInstructions(instructions);
    while (!this.instructions.isEmpty()) {
      Statement statement = this.instructions.pop();
      if (isLetStatement(statement)) {
        Statement.LetStmt stmt = (Statement.LetStmt) statement;
        System.out.println("Defining word " + stmt.getName());
      } else if (isExpression(statement)) {
        Statement.ExpressionStmt stmt = (Statement.ExpressionStmt) statement;
        Expression e = stmt.getExpression();
        this.apply(e);
      }
    }
  }

  public void setInstructions(List<Statement> statements) {
    new LinkedList<>(statements).descendingIterator().forEachRemaining(this.instructions::push);
  }

  private boolean isLetStatement(Statement statement) {
    return (statement instanceof Statement.LetStmt);
  }

  private boolean isExpression(Statement statement) {
    return (statement instanceof Statement.ExpressionStmt);
  }
}
