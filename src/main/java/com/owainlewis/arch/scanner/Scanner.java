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
package com.owainlewis.arch.scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Scanner {

  static Pattern intPat =
      Pattern.compile(
          "([-+]?)(?:(0)|([1-9][0-9]*)|0[xX]([0-9A-Fa-f]+)|0([0-7]+)|([1-9][0-9]?)[rR]([0-9A-Za-z]+)|0[0-9]+)(N)?");

  static Pattern floatPat = Pattern.compile("([-+]?[0-9]+(\\.[0-9]*)?([eE][-+]?[0-9]+)?)(M)?");

  private static final Map<String, TokenType> reservedWords;

  static {
    reservedWords =
        Map.of(
            "module", TokenType.MODULE,
            "private", TokenType.PRIVATE,
            "public", TokenType.PUBLIC,
            "let", TokenType.LET,
            "import", TokenType.IMPORT,
            "as", TokenType.AS);
  }

  private static class ScannerException extends RuntimeException {
    public final int line;
    public final int column;

    public ScannerException(int line, int column, Throwable cause) {
      super(cause);
      this.line = line;
      this.column = column;
    }

    @Override
    public String getMessage() {
      return String.format("Exception: %s. Line: %d, column: %d", super.getMessage(), line, column);
    }
  }

  private final Source source;

  private boolean hasMoreTokens = true;

  /** The list of tokens identified by the scanner */
  private final List<Token> tokens = new ArrayList<>();

  public Scanner(Source source) {
    this.source = source;
  }

  public List<Token> scan() throws IOException {
    List<Token> tokens = new ArrayList<>();
    while (true) {
      Token next = nextToken();
      tokens.add(next);
      if (TokenType.EOF.equals(Objects.requireNonNull(next).getType())) break;
    }

    return tokens;
  }

  public Token nextToken() throws IOException {
    // Skip whitespace
    char c = source.nextChar();

    while (Character.isWhitespace(c)) {
      c = source.nextChar();
    }

    if (source.isEOF()) return makeToken(TokenType.EOF, "", null);

    try {
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
        case '"':
          return readString();
        case '+':
        case '-':
          if (source.nextCharSatisfies(Character::isDigit)) {
            return readNumber(c);
          }
        default:
          {
            if (Character.isDigit(c)) {
              return readNumber(c);
            }

            if (isIdentifier(c)) {
              return readIdentifier(c);
            }
          }
      }
    } catch (Exception e) {
      throw new ScannerException(source.getLineNumber(), source.getColumnNumber(), e);
    }

    return null;
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

  private Token readString() throws IOException {
    StringBuilder builder = new StringBuilder();
    String rest = consumeWhile((c) -> c != '"');
    builder.append(rest);

    String lexeme = builder.toString();
    // Consume the last quote
    source.nextChar();

    return makeToken(TokenType.STRING, lexeme, lexeme);
  }

  //////////////////////////////////////////////////////////////////////
  // Identifiers
  //////////////////////////////////////////////////////////////////////

  private Token readIdentifier(char initChar) throws IOException {
    StringBuilder builder = new StringBuilder();
    builder.append(initChar);

    String rest = consumeWhile(this::isValidSymbolic);

    builder.append(rest);

    String lexeme = builder.toString();

    if (isKeyword(lexeme)) {
      TokenType type = reservedWords.get(lexeme);
      return makeToken(type, lexeme, null);
    }

    return makeToken(TokenType.IDENTIFIER, lexeme, lexeme);
  }

  private boolean isIdentifier(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '-' || c == '=' || c == '+';
  }

  private boolean isValidSymbolic(char c) {
    return c != ';' && !Character.isWhitespace(c);
  }

  //////////////////////////////////////////////////////////////////////
  // Numbers
  //////////////////////////////////////////////////////////////////////

  private Token readNumber(char initChar) throws IOException {
    String number = consumeNumber(initChar);
    Matcher m = intPat.matcher(number);
    if (m.matches()) {
      Integer i = Integer.parseInt(number);
      return makeToken(TokenType.INTEGER, number, i);
    }
    m = floatPat.matcher(number);
    if (m.matches()) {
      Double i = Double.parseDouble(number);
      return makeToken(TokenType.FLOAT, number, i);
    }

    throw new NumberFormatException("Unexpected numeric format for " + number);
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

  //////////////////////////////////////////////////////////////////////
  // Utils
  //////////////////////////////////////////////////////////////////////

  protected boolean isKeyword(String word) {
    return reservedWords.containsKey(word);
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
}
