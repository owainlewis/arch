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
