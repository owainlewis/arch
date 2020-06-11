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

import lombok.NonNull;

import java.util.List;
import java.util.Objects;

public abstract class Statement {

  static class ExpressionStmt extends Statement {
    private final Expression expression;

    ExpressionStmt(@NonNull Expression expression) {
      this.expression = expression;
    }

    public Expression getExpression() {
      return expression;
    }

    @Override
    public String toString() {
      return "ExpressionStmt{" + "expression=" + expression + '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ExpressionStmt that = (ExpressionStmt) o;
      return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
      return Objects.hash(expression);
    }
  }

  /**
   * A <strong>let</strong> statement is a name bound to a list of expressions.
   *
   * <p>Names can be any valid identifier
   *
   * <p>The body of a let statement is a whitespace separated sequence of expressions
   *
   * <p>For example the following is a valid let expression:
   *
   * <pre>let x = 10 ;</pre>
   *
   * and so is
   *
   * <pre>let y = [a] b ;</pre>
   */
  static class LetStmt extends Statement {
    private boolean isPrivate = false;
    private final String name;
    private final List<Expression> body;

    LetStmt(@NonNull String name, @NonNull List<Expression> body) {
      this.name = name;
      this.body = body;
    }

    LetStmt(@NonNull String name, @NonNull List<Expression> body, @NonNull boolean isPrivate) {
      this.name = name;
      this.body = body;
      this.isPrivate = isPrivate;
    }

    public boolean isPrivate() {
      return isPrivate;
    }

    public String getName() {
      return name;
    }

    public List<Expression> getBody() {
      return body;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      LetStmt letStmt = (LetStmt) o;
      return isPrivate == letStmt.isPrivate
          && Objects.equals(name, letStmt.name)
          && Objects.equals(body, letStmt.body);
    }

    @Override
    public int hashCode() {
      return Objects.hash(isPrivate, name, body);
    }

    @Override
    public String toString() {
      return "LetStmt{"
          + "isPrivate="
          + isPrivate
          + ", name='"
          + name
          + '\''
          + ", body="
          + body
          + '}';
    }
  }
}
