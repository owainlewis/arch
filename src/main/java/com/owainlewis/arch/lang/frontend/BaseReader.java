package com.owainlewis.arch.lang.frontend;

import java.io.IOException;
import java.util.function.Predicate;

public abstract class BaseReader implements Reader {
  private Source source;

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
