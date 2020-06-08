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
