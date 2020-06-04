package com.owainlewis.arch.lang;

public final class Token {

    private final TokenType type;

    private final String lexeme;

    private final Object literal;

    private final int lineNumber;

    private final int columnNumber;

    public Token(TokenType type, String lexeme, Object literal, int lineNumber, int columnNumber) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
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