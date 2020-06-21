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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

public final class Interpreter {

  private State state;

  private Map<String, BiFunction<Stack<Statement>, Stack<Expression>, Stack<Expression>>>
      dictionary = new HashMap<>();

  public Interpreter() {
    this.state = new State();
  }

  public void interpret(List<Statement> instructions) {
    state.setInstructions(instructions);
    while (state.hasInstructions()) {
      Statement statement = state.nextInstruction();
      if (isLetStatement(statement)) {
        Statement.LetStmt stmt = (Statement.LetStmt) statement;
        System.out.println("Defining word " + stmt.getName());
      } else if (isExpression(statement)) {
        Statement.ExpressionStmt stmt = (Statement.ExpressionStmt) statement;
        Expression e = stmt.getExpression();
        state.apply(e);
      }
    }
  }

  private void executeWord(String word) {
  }

  private boolean isLetStatement(Statement statement) {
    return (statement instanceof Statement.LetStmt);
  }

  private boolean isExpression(Statement statement) {
    return (statement instanceof Statement.ExpressionStmt);
  }
}
