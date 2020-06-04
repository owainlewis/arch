package com.owainlewis.arch.lang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class Scanner {

  private static class ScannerException extends RuntimeException {
    public final int line;
    public final int column;

    public ScannerException(int line, int column, Throwable cause) {
      super(cause);
      this.line = line;
      this.column = column;
    }
  }

  private final Source source;

  private boolean hasMoreTokens = true;

  /** The list of tokens identified by the scanner */
  private final List<Token> tokens = new ArrayList<>();

  public Scanner(Source source) {
    this.source = source;
  }

  // Atom
  // Integer
  // Float
  public Token nextToken() throws IOException {
    // Skip whitespace
    char c = source.nextChar();
    while(Character.isWhitespace(c)) {
      c = source.nextChar();
    }

    switch (c) {
      case '[':
        return makeUnaryCharToken(TokenType.LEFT_BRACKET, c);
      case ']':
        return makeUnaryCharToken(TokenType.RIGHT_BRACKET, c);
      case '=':
        return makeUnaryCharToken(TokenType.EQ, c);
      case ';':
        return makeUnaryCharToken(TokenType.SEMICOLON, c);
      case '>':
            readComment();
            break;
      default:
        {
          if (Character.isDigit(c)) {
            return readNumber(c);
          }
        }
    }

    return null;
  }

  private Token readNumber(char initChar) throws IOException {
    String number = consumeNumber(initChar);
    Integer i = Integer.parseInt(number);

    return makeToken(TokenType.INTEGER, number, i);
  }

  /**
   * Comments begin with a > char and run until the end of a line
   *
   * @throws IOException
   */
  private void readComment() throws IOException {
    while (true) {
      char c = source.nextChar();
      if (c == '\n') break;

      if (source.isEOF()) {
        throw new IllegalStateException("Unterminated comment literal while reading comment");
      }
    }
  }

  private String consumeNumber(char initDigit) throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append(initDigit);

    String lhs = consumeWhile(Character::isDigit);
    builder.append(lhs);

    if (source.nextCharIs('.')) {
      source.nextChar();
      builder.append(".");
      if (source.nextCharSatisfies(Character::isDigit)) {
        String rhs = consumeWhile(Character::isDigit);
        builder.append(rhs);
      }
    }

    return builder.toString();
  }

  private String consumeWhile(Predicate<Character> predicate) throws IOException {
    StringBuilder builder = new StringBuilder();
    while (true) {
      if (source.nextCharSatisfies(predicate)) {
        char c = source.nextChar();
        builder.append(c);
      } else {
        break;
      }
    }

    return builder.toString();
  }

  private Token makeUnaryCharToken(TokenType type, char c) {
    return makeToken(type, Character.toString(c), null);
  }

  private Token makeToken(TokenType type, String lexeme, Object literal) {
    return new Token(type, lexeme, literal, source.getLineNumber(), source.getColumnNumber());
  }

  public Exception error(Throwable cause) {
    return new ScannerException(source.getLineNumber(), source.getColumnNumber(), cause);
  }
}
