package com.owainlewis.arch.lang;

import java.util.Optional;

public class Token {

    private TokenType type;

    private String lexeme;

    private Object literal;

    private int lineNumber;

    private int columnNumber;

    private SourceReader source;

    public Token(SourceReader source) {
        this.source = source;
    }

    public void extract() {
        Optional<Character> c = source.nextChar();

    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public Object getLiteral() {
        return literal;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                ", literal=" + literal +
                ", lineNumber=" + lineNumber +
                ", columnNumber=" + columnNumber +
                '}';
    }
}