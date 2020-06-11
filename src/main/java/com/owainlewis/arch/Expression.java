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

public abstract class Expression {

    public enum Type {
        Integer,
        Float,
        String,
        Word,
    }

    static final class Literal extends Expression {
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
            return "Literal{" +
                    "type=" + type +
                    ", value=" + value +
                    '}';
        }
    }

    static final class ListExpr extends Expression {
        private final List<Expression> expressions;

        public ListExpr(List<Expression> expressions) {
            this.expressions = expressions;
        }

        public List<Expression> getExpressions() {
            return expressions;
        }

        @Override
        public String toString() {
            return "ListExpr{" +
                    "expressions=" + expressions +
                    '}';
        }
    }
}
