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
import java.util.Objects;

public abstract class Expression {

  public enum Type {
    Integer,
    Float,
    String,
    Word,
    List,
  }

  public abstract Type getType();

  public abstract Object getValue();

  public static final class Literal extends Expression {
    private final Type type;
    private final Object value;

    Literal(Type type, Object value) {
      this.type = type;
      this.value = value;
    }

    public Object getValue() {
      return value;
    }

    public Type getType() {
      return type;
    }

    @Override
    public String toString() {
      return "Literal{" + "type=" + type + ", value=" + value + '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Literal literal = (Literal) o;
      return type == literal.type && Objects.equals(value, literal.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, value);
    }
  }

  public static final class ListExpr extends Expression {
    private final List<Expression> expressions;

    public ListExpr(List<Expression> expressions) {
      this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
      return expressions;
    }

    public Type getType() {
      return Type.List;
    }

    public Object getValue() {
      return expressions;
    }

    @Override
    public String toString() {
      return "ListExpr{" + "expressions=" + expressions + '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ListExpr listExpr = (ListExpr) o;
      return Objects.equals(expressions, listExpr.expressions);
    }

    @Override
    public int hashCode() {
      return Objects.hash(expressions);
    }
  }
}
