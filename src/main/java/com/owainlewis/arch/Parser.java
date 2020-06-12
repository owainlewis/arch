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

import com.owainlewis.arch.scanner.Token;
import com.owainlewis.arch.scanner.TokenType;

import java.util.ArrayList;
import java.util.List;

public final class Parser {

  private final List<Token> tokens;

  private int current = 0;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  public List<Statement> parse() {
    List<Statement> statements = new ArrayList<>();

    for (Token token : tokens) {
      if (isAtEnd()) {
        break;
      }
      Statement stmt = statement();
      statements.add(stmt);
    }

    return statements;
  }

  private Expression expression() {
    if (match(TokenType.INTEGER)) {
      return new Expression.Literal(Expression.Type.Integer, previous().getLiteral());
    }
    if (match(TokenType.FLOAT)) {
      return new Expression.Literal(Expression.Type.Float, previous().getLiteral());
    }
    if (match(TokenType.STRING)) {
      return new Expression.Literal(Expression.Type.String, previous().getLiteral());
    }
    if (match(TokenType.IDENTIFIER)) {
      return new Expression.Literal(Expression.Type.Word, previous().getLiteral());
    }
    if (match(TokenType.LEFT_BRACKET)) {
      List<Expression> statements = new ArrayList<>();
      while (!nextTokenIs(TokenType.RIGHT_BRACKET)) {
        statements.add(expression());
      }

      consume(TokenType.RIGHT_BRACKET, "Expect ']' after block.");
      return new Expression.ListExpr(statements);
    }

    throw new IllegalStateException("Could not read expression " + peek());
  }

  private Statement statement() {
      Statement statement;

      if (nextTokenIs(TokenType.LET)) {
        return letStatement();
    }
        Expression expr = expression();
        return new Statement.ExpressionStmt(expr);
  }

  private Statement letStatement() {
    // let x = 10 ;
    consume(TokenType.LET, "Let expected");
    Token ident = consume(TokenType.IDENTIFIER, "Expect identifier");
    consume(TokenType.EQ, "Expect '=' in let statement");

    List<Expression> expressions = new ArrayList<>();
    while (!nextTokenIs(TokenType.SEMICOLON)) {
      Expression e = expression();
      expressions.add(e);
    }
    consume(TokenType.SEMICOLON, "Expect ';'");
    return new Statement.LetStmt(ident.getLexeme(), expressions);
  }

  private boolean match(TokenType... types) {
    for (TokenType type : types) {
      if (nextTokenIs(type)) {
        advance();
        return true;
      }
    }

    return false;
  }

  private Token consume(TokenType type, String message) {
    if (nextTokenIs(type)) return advance();

    throw new IllegalStateException(message);
  }

  private boolean nextTokenIs(TokenType type) {
    if (isAtEnd()) return false;
    return peek().getType() == type;
  }

  private Token advance() {
    if (!isAtEnd()) current++;
    return previous();
  }

  private boolean isAtEnd() {
    return peek().getType() == TokenType.EOF;
  }

  private Token peek() {
    return tokens.get(current);
  }

  private Token previous() {
    return tokens.get(current - 1);
  }
}
