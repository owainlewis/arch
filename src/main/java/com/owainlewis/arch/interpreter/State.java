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

public final class State {

  private Stack<Statement> instructions = new Stack<>();

  private Stack<Expression> stack = new Stack<>();

  /** Internal dictionary * */
  private Map<String, BiFunction<Stack<Statement>, Stack<Expression>, Stack<Expression>>>
      dictionary = new HashMap<>();

  /** User dictionary * */
  private Map<String, List<Expression>> userDictionary = new HashMap<>();

  public State() {
    this.dictionary =
        Map.of(
            "debug", Operations.debug,
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
      push(e);
    }
  }

  // Instructions

  public void setInstructions(List<Statement> statements) {
    new LinkedList<>(statements).descendingIterator().forEachRemaining(this.instructions::push);
  }

  public Statement nextInstruction() {
    return instructions.pop();
  }

  public boolean hasInstructions() {
    return !instructions.isEmpty();
  }

  // Stack ops

  public void push(Expression e) {
    stack.push(e);
  }

  // Dictionary

  public void defineWord(String word, List<Expression> expressions) {
    userDictionary.put(word, expressions);
  }

  public BiFunction<Stack<Statement>, Stack<Expression>, Stack<Expression>> getNativeWord(
      String word) {
    return dictionary.get(word);
  }

  public boolean wordIsDefined(String word) {
    return dictionary.containsKey(word) || userDictionary.containsKey(word);
  }
}
