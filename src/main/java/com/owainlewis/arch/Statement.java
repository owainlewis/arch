package com.owainlewis.arch;

import com.owainlewis.arch.lang.scanner.Token;
import lombok.NonNull;

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
            return "ExpressionStmt{" +
                    "expression=" + expression +
                    '}';
        }
    }

    static class LetStmt extends Statement {
        private boolean isPrivate = false;
        private final Token name;
        private final Expression value;

        LetStmt(@NonNull Token name, @NonNull Expression value) {
            this.name = name;
            this.value = value;
        }

        LetStmt(@NonNull Token name, @NonNull Expression value, @NonNull boolean isPrivate) {
            this.name = name;
            this.value = value;
            this.isPrivate = isPrivate;
        }

        public boolean isPrivate() {
            return isPrivate;
        }

        public Token getName() {
            return name;
        }

        public Expression getValue() {
            return value;
        }
    }
}
