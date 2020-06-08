package com.owainlewis.arch;

import java.util.List;

public abstract class Expression {

    static final class Literal extends Expression {
        private final ExpressionType type;
        private final Object value;

        Literal(ExpressionType type, Object value) {
            this.type = type;
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Literal{" +
                    "value=" + value +
                    '}';
        }

        public ExpressionType getType() {
            return type;
        }
    }

    static final class ListExpr extends Expression {
        private final List<Expression> expressions;

        public ListExpr(List<Expression> expressions) {
            this.expressions = expressions;
        }
    }
}
