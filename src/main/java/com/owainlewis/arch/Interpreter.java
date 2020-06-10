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

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Stack;

@NoArgsConstructor
public final class Interpreter {

    private Stack<Expression> runtimeStack = new Stack<>();

    public void interpret(List<Statement> statements) {
        for (Statement statement : statements) {
            if (statement instanceof Statement.ExpressionStmt) {
                Statement.ExpressionStmt stmt = (Statement.ExpressionStmt) statement;
                runtimeStack.push(stmt.getExpression());
                System.out.println(runtimeStack.size());
            }
        }
    };
}
