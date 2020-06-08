package com.owainlewis.arch;

import com.owainlewis.arch.lang.scanner.Token;
import com.owainlewis.arch.lang.scanner.TokenType;

import java.util.ArrayList;
import java.util.List;

public final class Parser {

  private final List<Token> tokens;

  private int current = 0;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
    System.out.println(tokens);
  }

  public List<Statement> parse() {
    List<Statement> statements = new ArrayList<>();

    for (Token token : tokens) {
      if (token.getType() == TokenType.EOF) {
        break;
      }
      Statement stmt = statement();
      statements.add(stmt);
    }

    return statements;
  }

  private Expression expression() {
    if (match(TokenType.INTEGER)) {
      return new Expression.Literal(ExpressionType.Integer, previous().getLiteral());
    }
    if (match(TokenType.FLOAT)) {
      return new Expression.Literal(ExpressionType.Float, previous().getLiteral());
    }
    if (match(TokenType.STRING)) {
      return new Expression.Literal(ExpressionType.String, previous().getLiteral());
    }
    if (match(TokenType.IDENTIFIER)) {
      return new Expression.Literal(ExpressionType.Word, previous().getLiteral());
    }

    if (match(TokenType.LEFT_BRACKET)) return new Expression.ListExpr(block());

    if (match(TokenType.EOF)) {
        System.out.println("EOF"); return null;
    }

    return null;
  }

    private List<Expression> block() {
        List<Expression> statements = new ArrayList<>();

        while (!check(TokenType.RIGHT_BRACKET) && !isAtEnd()) {
            //statements.add(Expression.Literal(ExpressionType.Int));
        }

        consume(TokenType.RIGHT_BRACKET, "Expect ']' after block.");

        return statements;
    }

  private Statement statement() {
      Statement statement = expressionStatement();
      return statement;
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
    System.out.println("PEEK >> " + peek().getType());
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
