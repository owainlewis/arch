package com.owainlewis.arch.lang;

import java.util.Optional;

public final class Scanner {

    private final SourceReader source;

    private Token currentToken;

    public Scanner(SourceReader source) {
        this.source = source;
    }

    public Token currentToken() {
        return currentToken;
    }

    public Token nextToken() {
        Optional<Character> c = source.nextChar();
        return new Token(TokenType.EOF, null, null, source.getLineNumber(), source.getColumnNumber());
    }
}
