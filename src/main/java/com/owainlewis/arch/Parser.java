package com.owainlewis.arch;

import com.owainlewis.arch.lang.frontend.Token;
import com.owainlewis.arch.lang.frontend.TokenType;

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
      if (token.getType() == TokenType.EOF) {
        break;
      }
      Statement stmt = expressionStatement();
      statements.add(stmt);
    }

    return statements;
  }

  private Expression expression() {
    if (match(TokenType.INTEGER, TokenType.FLOAT, TokenType.STRING, TokenType.IDENTIFIER)) {
      return new Expression.Literal(previous().getLiteral());
    }

    if (match(TokenType.LEFT_BRACKET)) {
      List<Expression> exprs = listExpression();
      return new Expression.List(exprs);
    }

    throw new IllegalStateException(peek().toString());
  }

  private List<Expression> listExpression() {
    List<Expression> exprs = new ArrayList<>();

    while (!check(TokenType.RIGHT_BRACKET)) {
      exprs.add(expression());
    }

    consume(TokenType.RIGHT_BRACKET, "Expect ']' after block.");
    return exprs;
  }

  private Statement expressionStatement() {
    Expression expr = expression();
    return new Statement.ExpressionStmt(expr);
  }

  private boolean match(TokenType... types) {
    for (TokenType type : types) {
      if (check(type)) {
        advance();
        return true;
      }
    }

    return false;
  }

  private Token consume(TokenType type, String message) {
    if (check(type)) return advance();

    throw new IllegalStateException(message);
  }

  private boolean check(TokenType type) {
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
